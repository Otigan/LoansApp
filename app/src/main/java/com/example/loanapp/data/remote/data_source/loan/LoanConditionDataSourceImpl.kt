package com.example.loanapp.data.remote.data_source.loan

import com.example.loanapp.data.remote.api.LoansApi
import com.example.loanapp.domain.entity.LoanCondition
import javax.inject.Inject

class LoanConditionDataSourceImpl @Inject constructor(private val loansApi: LoansApi) :
    LoanConditionDataSource {

    override suspend fun getLoanCondition(): LoanCondition =
        loansApi.getLoanCondition()

}