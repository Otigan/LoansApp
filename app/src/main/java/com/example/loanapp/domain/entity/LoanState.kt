package com.example.loanapp.domain.entity

enum class LoanState(val state: String) {
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    REGISTERED("REGISTERED"),
}