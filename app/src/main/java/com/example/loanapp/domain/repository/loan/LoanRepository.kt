package com.example.loanapp.domain.repository.loan

import com.example.loanapp.data.remote.model.LoanRequestBody
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.entity.LoanCondition
import com.example.loanapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface LoanRepository {

    fun getLoans(): Flow<Resource<List<Loan>>>

    suspend fun requestLoan(
        loanRequestBody: LoanRequestBody
    ): Resource<Loan>

    suspend fun getLoanCondition(): Resource<LoanCondition>


}