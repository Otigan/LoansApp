package com.example.loanapp.domain.use_case.loan

import com.example.loanapp.util.Resource
import com.example.loanapp.domain.entity.LoanCondition
import com.example.loanapp.domain.repository.LoanRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoanConditionUseCase @Inject constructor(private val loansRepository: LoanRepository) {

    suspend operator fun invoke(name: String): Flow<Resource<LoanCondition>> =
        loansRepository.getLoanCondition(name)

}