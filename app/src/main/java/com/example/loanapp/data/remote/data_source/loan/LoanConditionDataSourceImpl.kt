package com.example.loanapp.data.remote.data_source.loan

import com.example.loanapp.data.remote.api.LoansApi
import com.example.loanapp.domain.entity.LoanCondition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class LoanConditionDataSourceImpl @Inject constructor(private val loansApi: LoansApi) :
    LoanConditionDataSource {

    override suspend fun getLoanCondition(token: String?): Flow<Response<LoanCondition>> =
        flow {
            emit(loansApi.getLoanCondition(token!!))
        }
}