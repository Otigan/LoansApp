package com.example.loanapp.domain.use_case

import com.example.loanapp.domain.repository.LoanRepository
import javax.inject.Inject

class GetAllLoansUseCase @Inject constructor(private val loansRepository: LoanRepository) {

    suspend operator fun invoke(name: String) = loansRepository.getAllLoans(name)

}