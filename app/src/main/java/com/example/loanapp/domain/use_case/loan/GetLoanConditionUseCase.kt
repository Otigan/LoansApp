package com.example.loanapp.domain.use_case.loan

import com.example.loanapp.domain.entity.LoanCondition
import com.example.loanapp.domain.repository.loan.LoanRepository
import com.example.loanapp.util.Resource
import javax.inject.Inject

class GetLoanConditionUseCase @Inject constructor(private val loansRepository: LoanRepository) {

    suspend operator fun invoke(name: String): Resource<LoanCondition> =
        loansRepository.getLoanCondition(name)

}