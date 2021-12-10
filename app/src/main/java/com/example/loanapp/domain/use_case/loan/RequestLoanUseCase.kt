package com.example.loanapp.domain.use_case.loan

import com.example.loanapp.data.remote.model.LoanRequestBody
import com.example.loanapp.util.Resource
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.repository.LoanRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestLoanUseCase @Inject constructor(private val loanRepository: LoanRepository) {

    suspend operator fun invoke(
        token: String,
        loanRequestBody: LoanRequestBody
    ): Flow<Resource<Loan>> =
        loanRepository.requestLoan(token, loanRequestBody)

}