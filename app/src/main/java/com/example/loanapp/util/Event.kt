package com.example.loanapp.util

sealed class BaseEvent {
    class Success: BaseEvent()
    data class Error(val message: String): BaseEvent()
    class Loading: BaseEvent()
}

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

