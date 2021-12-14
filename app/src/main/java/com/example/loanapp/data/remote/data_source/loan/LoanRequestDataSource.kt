package com.example.loanapp.data.remote.data_source.loan

import com.example.loanapp.data.remote.model.LoanDto
import com.example.loanapp.data.remote.model.LoanRequestBody

interface LoanRequestDataSource {

    suspend fun requestLoan(
        loanRequestBody: LoanRequestBody
    ): LoanDto

}