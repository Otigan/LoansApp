package com.example.loanapp.data.remote.repository

import androidx.room.withTransaction
import com.example.loanapp.data.local.db.LoansDatabase
import com.example.loanapp.data.remote.data_source.loan.LoanConditionDataSource
import com.example.loanapp.data.remote.data_source.loan.LoanDataSource
import com.example.loanapp.data.remote.data_source.loan.LoanRequestDataSource
import com.example.loanapp.data.remote.mapper.LoanDtoMapper
import com.example.loanapp.data.remote.model.LoanRequestBody
import com.example.loanapp.data.remote.util.LoanRepositoryErrorHandler
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.entity.LoanCondition
import com.example.loanapp.domain.repository.LoanRepository
import com.example.loanapp.util.Common.returnUnknownError
import com.example.loanapp.util.Resource
import com.example.loanapp.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(
    private val loanDataSource: LoanDataSource,
    private val loanConditionDataSource: LoanConditionDataSource,
    private val loanRequestDataSource: LoanRequestDataSource,
    private val loanRepositoryErrorHandler: LoanRepositoryErrorHandler,
    private val loansDatabase: LoansDatabase,
) : LoanRepository {

    private val loansDao = loansDatabase.getLoansDao()

    override fun getLoans(token: String) = networkBoundResource(
        query = {
            loansDao.getAllLoans()
        },
        fetch = {
            loanDataSource.getAllLoans(token)
        },
        saveFetchResult = { loan_list ->
            val loans = loan_list.map { loanDto ->
                LoanDtoMapper().mapToLoanEntity(loanDto)
            }
            loansDatabase.withTransaction {
                loansDao.deleteAllLoans()
                loansDao.insertLoans(loans)
            }
        }
    )

    /*override suspend fun getAllLoans(token: String): Flow<Resource<List<Loan>>> =
        loanDataSource.getAllLoans(token).map { response ->
            if (response.isSuccessful && response.code() == 200) {
                response.body()?.let { listDto ->
                    val loans = listDto.map { loanDto ->
                        LoanDtoMapper().mapToDomainModel(loanDto)
                    }
                    Resource.Success(loans)
                } ?: returnUnknownError()
            } else {
                val errorMessage = loanRepositoryErrorHandler(response.code())
                Resource.Error(errorMessage = errorMessage)
            }
        }*/

    override suspend fun requestLoan(
        token: String,
        loanRequestBody: LoanRequestBody
    ): Flow<Resource<Loan>> =
        loanRequestDataSource.requestLoan(token, loanRequestBody).map { response ->
            if (response.isSuccessful && response.code() == 200 || response.code() == 201) {
                response.body()?.let { loanDto ->
                    loanDto.let {
                        Resource.Success(
                            LoanDtoMapper().mapToDomainModel(it)
                        )
                    }
                } ?: returnUnknownError()
            } else {
                val errorMessage = loanRepositoryErrorHandler(response.code())
                Resource.Error(errorMessage = errorMessage)
            }

        }

    override suspend fun getLoanCondition(token: String): Flow<Resource<LoanCondition>> =
        loanConditionDataSource.getLoanCondition(token).map { response ->
            if (response.isSuccessful && response.code() == 200) {
                response.body()?.let {
                    Resource.Success(it)
                } ?: returnUnknownError()
            } else {
                val errorMessage = loanRepositoryErrorHandler(response.code())
                Resource.Error(errorMessage = errorMessage)
            }
        }

}