package com.example.loanapp.domain.use_case

import com.example.loanapp.domain.entity.LoanCondition
import com.example.loanapp.domain.repository.LoanRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetLoanConditionUseCase @Inject constructor(private val loansRepository: LoanRepository) {

    suspend operator fun invoke(name: String): Flow<Response<LoanCondition>> =
        loansRepository.getLoanCondition(name)

}