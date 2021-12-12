package com.example.loanapp.domain.entity

//TODO: текст не используется и эти же значения можно получить из поля name конкретного елемента enum
enum class LoanState(val state: String) {
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    REGISTERED("REGISTERED")
}