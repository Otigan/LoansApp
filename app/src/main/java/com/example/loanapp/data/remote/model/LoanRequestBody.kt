package com.example.loanapp.data.remote.model

data class LoanRequestBody(
    val amount: Int,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String
)