package com.example.loanapp.util


sealed class AuthEvent {
    object Success : AuthEvent()
    data class Error(val message: String) : AuthEvent()
    object Loading : AuthEvent()
    object Logout : AuthEvent()
}

