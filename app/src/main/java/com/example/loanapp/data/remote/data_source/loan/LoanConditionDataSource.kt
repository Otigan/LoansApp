package com.example.loanapp.data.remote.data_source.loan

import com.example.loanapp.domain.entity.LoanCondition
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface LoanConditionDataSource {

    suspend fun getLoanCondition(token: String?): Flow<Response<LoanCondition>>

}