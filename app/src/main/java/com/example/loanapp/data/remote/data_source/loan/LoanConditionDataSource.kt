package com.example.loanapp.data.remote.data_source.loan

import com.example.loanapp.domain.entity.LoanCondition

interface LoanConditionDataSource {

    suspend fun getLoanCondition(token: String?): LoanCondition

}