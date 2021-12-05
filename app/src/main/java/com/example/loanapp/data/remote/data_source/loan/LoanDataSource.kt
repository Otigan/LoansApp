package com.example.loanapp.data.remote.data_source.loan

import com.example.loanapp.data.remote.model.LoanDto
import com.example.loanapp.data.remote.model.LoanRequestBody
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface LoanDataSource {

    suspend fun getAllLoans(token: String?): Flow<Response<List<LoanDto>>>
}