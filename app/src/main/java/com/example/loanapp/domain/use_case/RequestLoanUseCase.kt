package com.example.loanapp.domain.use_case

import com.example.loanapp.data.remote.model.LoanRequestBody
import com.example.loanapp.domain.repository.LoanRepository
import javax.inject.Inject

class RequestLoanUseCase @Inject constructor(private val loanRepository: LoanRepository) {

    suspend operator fun invoke(name: String, loanRequestBody: LoanRequestBody) =
        loanRepository.requestLoan(name, loanRequestBody)

}