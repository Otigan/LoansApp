package com.example.loanapp.data.remote.data_source.loan

import com.example.loanapp.data.remote.api.LoansApi
import com.example.loanapp.data.remote.model.LoanDto
import javax.inject.Inject

class LoanDataSourceImpl @Inject constructor(private val loansApi: LoansApi) : LoanDataSource {

    override suspend fun getAllLoans(token: String?): List<LoanDto> =
        loansApi.getAllLoans(token!!)

}