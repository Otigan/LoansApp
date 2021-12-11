package com.example.loanapp.data.remote.data_source.loan

import com.example.loanapp.data.remote.api.LoansApi
import com.example.loanapp.data.remote.model.LoanDto
import com.example.loanapp.data.remote.model.LoanRequestBody
import javax.inject.Inject

class LoanRequestDataSourceImpl @Inject constructor(private val loansApi: LoansApi) :
    LoanRequestDataSource {

    override suspend fun requestLoan(
        token: String?,
        loanRequestBody: LoanRequestBody
    ): LoanDto =
        loansApi.requestLoan(token!!, loanRequestBody)

}