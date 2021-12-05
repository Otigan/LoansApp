package com.example.loanapp.domain.entity

data class LoanCondition(
    val maxAmount: Int,
    val percent: Double,
    val period: Int
)