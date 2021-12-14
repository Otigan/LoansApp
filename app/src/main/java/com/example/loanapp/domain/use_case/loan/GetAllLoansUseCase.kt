package com.example.loanapp.domain.use_case.loan

import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.repository.loan.LoanRepository
import com.example.loanapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllLoansUseCase @Inject constructor(private val loansRepository: LoanRepository) {

    operator fun invoke(): Flow<Resource<List<Loan>>> =
        loansRepository.getLoans()

}