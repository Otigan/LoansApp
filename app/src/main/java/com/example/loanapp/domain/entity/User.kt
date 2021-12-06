package com.example.loanapp.domain.entity

data class User(
    val name: String? = null,
    val role: String? = null,
    var password: String? = null
)