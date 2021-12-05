package com.example.loanapp.data.remote.data_source.loan

import com.example.loanapp.data.api.LoansApi
import com.example.loanapp.data.remote.model.LoanDto
import com.example.loanapp.data.remote.model.LoanRequestBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class LoanRequestDataSourceImpl @Inject constructor(private val loansApi: LoansApi) :
    LoanRequestDataSource {

    override suspend fun requestLoan(
        token: String?,
        loanRequestBody: LoanRequestBody
    ): Flow<Response<LoanDto>> =
        flow {
            emit(loansApi.requestLoan(token!!, loanRequestBody))
        }
}