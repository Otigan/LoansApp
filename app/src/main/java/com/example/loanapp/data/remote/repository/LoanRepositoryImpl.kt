package com.example.loanapp.data.remote.repository

import com.example.loanapp.data.remote.data_source.loan.LoanConditionDataSource
import com.example.loanapp.data.remote.data_source.loan.LoanDataSource
import com.example.loanapp.data.remote.data_source.loan.LoanRequestDataSource
import com.example.loanapp.data.remote.mapper.LoanDtoMapper
import com.example.loanapp.data.remote.model.LoanRequestBody
import com.example.loanapp.domain.Resource
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.entity.LoanCondition
import com.example.loanapp.domain.repository.LoanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(
    private val loanDataSource: LoanDataSource,
    private val loanConditionDataSource: LoanConditionDataSource,
    private val loanRequestDataSource: LoanRequestDataSource,
) :
    LoanRepository {

    override suspend fun getAllLoans(token: String): Flow<Resource<List<Loan>>> =
        loanDataSource.getAllLoans(token).map { response ->
            if (response.isSuccessful && response.code() == 200) {
                response.body()?.let { listDto ->
                    val loans = listDto.map { loanDto ->
                        LoanDtoMapper().mapToDomainModel(loanDto)
                    }
                    Resource.Success(loans)
                } ?: Resource.Error("Произошла неизвестная ошибка 1")
            } else {
                when (response.code()) {
                    401 -> {
                        Resource.Error("Пользователь не авторизован")
                    }
                    403 -> {
                        Resource.Error("Forbidden")
                    }
                    404 -> {
                        Resource.Error("Not found")
                    }
                    else -> {
                        Resource.Error("Произошла неизвестная ошибка 2")
                    }
                }
            }
        }

    override suspend fun requestLoan(
        token: String,
        loanRequestBody: LoanRequestBody
    ): Flow<Resource<Loan>> =
        loanRequestDataSource.requestLoan(token, loanRequestBody).map { response ->
            if (response.isSuccessful && response.code() == 200) {
                response.body()?.let { loanDto ->
                    loanDto.let {
                        Resource.Success(
                            LoanDtoMapper().mapToDomainModel(it)
                        )
                    }
                } ?: Resource.Error("Произошла неизвестная ошибка")
            } else {
                when (response.code()) {
                    401 -> {
                        Resource.Error("Пользователь не авторизован")
                    }
                    403 -> {
                        Resource.Error("Forbidden")
                    }
                    404 -> {
                        Resource.Error("Not found")
                    }
                    else -> {
                        Resource.Error("Произошла неизвестная ошибка 2")
                    }
                }
            }

        }

    override suspend fun getLoanCondition(token: String): Flow<Resource<LoanCondition>> =
        loanConditionDataSource.getLoanCondition(token).map { response ->
            if (response.isSuccessful && response.code() == 200) {
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Произошла неизвестная ошибка")
            } else {
                when (response.code()) {
                    401 -> {
                        Resource.Error("Пользователь не авторизован")
                    }
                    403 -> {
                        Resource.Error("Forbidden")
                    }
                    404 -> {
                        Resource.Error("Not found")
                    }
                    else -> {
                        Resource.Error("Произошла неизвестная ошибка 2")
                    }
                }
            }
        }

}