package com.example.loanapp.data.remote.repository.loan

import androidx.room.withTransaction
import com.example.loanapp.data.local.db.LoansDatabase
import com.example.loanapp.data.local.mapper.LoanEntityMapper
import com.example.loanapp.data.remote.data_source.loan.LoanConditionDataSource
import com.example.loanapp.data.remote.data_source.loan.LoanDataSource
import com.example.loanapp.data.remote.data_source.loan.LoanRequestDataSource
import com.example.loanapp.data.remote.mapper.LoanMapper
import com.example.loanapp.data.remote.model.LoanRequestBody
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.entity.LoanCondition
import com.example.loanapp.domain.repository.loan.LoanRepository
import com.example.loanapp.util.Resource
import com.example.loanapp.util.ResponseHandler
import com.example.loanapp.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(
    private val loanDataSource: LoanDataSource,
    private val loanConditionDataSource: LoanConditionDataSource,
    private val loanRequestDataSource: LoanRequestDataSource,
    private val loansDatabase: LoansDatabase,
    private val responseHandler: ResponseHandler,
    private val loanMapper: LoanMapper,
    private val loanEntityMapper: LoanEntityMapper
) : LoanRepository {

    private val loansDao = loansDatabase.getLoansDao()

    override fun getLoans(token: String): Flow<Resource<List<Loan>>> = networkBoundResource(
        responseHandler = responseHandler,
        query = {
            loansDao.getAllLoans().map { loanEntities ->
                loanEntities.map { loanEntity ->
                    loanEntityMapper.mapToDomainModel(loanEntity)
                }
            }
        },
        fetch = {
            loanDataSource.getAllLoans(token)
        },
        saveFetchResult = { loans ->
            val loanEntities = loans.map { loanDto ->
                loanMapper.mapLoanDtoToLoanEntity(loanDto)
            }
            loansDatabase.withTransaction {
                loansDao.deleteAllLoans()
                loansDao.insertLoans(loanEntities)
            }
        },
    )

    override suspend fun requestLoan(
        token: String,
        loanRequestBody: LoanRequestBody
    ): Resource<Loan> {
        return try {
            val response = loanRequestDataSource.requestLoan(token, loanRequestBody)
            return responseHandler.handleSuccess(
                loanMapper.mapLoanDtoToLoan(response)
            )
        } catch (e: Exception) {
            responseHandler.handleException(e, null)
        }
    }

    override suspend fun getLoanCondition(token: String): Resource<LoanCondition> {
        return try {
            val response = loanConditionDataSource.getLoanCondition(token)
            return responseHandler.handleSuccess(
                response
            )
        } catch (e: Exception) {
            responseHandler.handleException(e, null)
        }
    }

}