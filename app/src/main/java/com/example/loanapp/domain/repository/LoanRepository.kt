package com.example.loanapp.domain.repository

import com.example.loanapp.data.remote.model.LoanDto
import com.example.loanapp.data.remote.model.LoanRequestBody
import com.example.loanapp.domain.entity.LoanCondition
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface LoanRepository {

    suspend fun getAllLoans(name: String): Flow<Response<List<LoanDto>>>

    suspend fun requestLoan(name: String, loanRequestBody: LoanRequestBody): Flow<Response<LoanDto>>

    suspend fun getLoanCondition(name: String): Flow<Response<LoanCondition>>


}