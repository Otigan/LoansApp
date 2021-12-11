package com.example.loanapp.util


sealed class LoginEvent {
    object Success : LoginEvent()
    data class Error(val message: String) : LoginEvent()
    object Loading : LoginEvent()
    object Logout : LoginEvent()
}

sealed class RegisterEvent {
    object Success : RegisterEvent()
    data class Error(val message: String) : RegisterEvent()
    object Loading : RegisterEvent()
}

