package com.example.loanapp.data.remote.data_source.loan

import com.example.loanapp.data.remote.model.LoanDto
import com.example.loanapp.data.remote.model.LoanRequestBody
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface LoanRequestDataSource {

    suspend fun requestLoan(
        token: String?,
        loanRequestBody: LoanRequestBody
    ): Flow<Response<LoanDto>>

}