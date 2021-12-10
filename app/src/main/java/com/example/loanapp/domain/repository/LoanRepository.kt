package com.example.loanapp.domain.repository

import com.example.loanapp.data.local.db.LoanEntity
import com.example.loanapp.data.remote.model.LoanRequestBody
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.entity.LoanCondition
import com.example.loanapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface LoanRepository {

    fun getLoans(token: String): Flow<Resource<List<LoanEntity>>>

    //suspend fun getAllLoans(token: String): Flow<Resource<List<Loan>>>

    suspend fun requestLoan(
        token: String,
        loanRequestBody: LoanRequestBody
    ): Flow<Resource<Loan>>

    suspend fun getLoanCondition(token: String): Flow<Resource<LoanCondition>>


}