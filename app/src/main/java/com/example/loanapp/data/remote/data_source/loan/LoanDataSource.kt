package com.example.loanapp.data.remote.data_source.loan

import com.example.loanapp.data.remote.model.LoanDto

interface LoanDataSource {

    suspend fun getAllLoans(): List<LoanDto>
}