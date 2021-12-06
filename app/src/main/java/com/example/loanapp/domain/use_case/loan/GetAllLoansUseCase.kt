package com.example.loanapp.domain.use_case.loan

import com.example.loanapp.domain.Resource
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.repository.LoanRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllLoansUseCase @Inject constructor(private val loansRepository: LoanRepository) {

    suspend operator fun invoke(token: String): Flow<Resource<List<Loan>>> =
        loansRepository.getAllLoans(token)

}