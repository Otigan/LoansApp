package com.example.loanapp.domain.use_case.loan

import com.example.loanapp.domain.repository.LoanRepository
import javax.inject.Inject

class GetAllLoansUseCase @Inject constructor(private val loansRepository: LoanRepository) {

    operator fun invoke(token: String) = loansRepository.getLoans(token)

    /*suspend operator fun invoke(token: String): Flow<Resource<List<Loan>>> =
        loansRepository.getAllLoans(token)*/

}