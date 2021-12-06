package com.example.loanapp.data.remote.data_source.loan

import com.example.loanapp.data.remote.api.LoansApi
import com.example.loanapp.data.remote.model.LoanDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class LoanDataSourceImpl @Inject constructor(private val loansApi: LoansApi) : LoanDataSource {

    override suspend fun getAllLoans(token: String?): Flow<Response<List<LoanDto>>> =
        flow {
            emit(loansApi.getAllLoans(token!!))
        }
}