package com.example.loanapp.util

import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.entity.LoanCondition


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

sealed class LoanEvent {
    data class Success(val loans: List<Loan>) : LoanEvent()
    data class Error(val message: String, val data: List<Loan>) : LoanEvent()
    object Loading : LoanEvent()
}

sealed class LoanConditionEvent {
    data class Success(val loanCondition: LoanCondition) : LoanConditionEvent()
    data class Error(val message: String) : LoanConditionEvent()
    object Loading : LoanConditionEvent()
}

sealed class LoanRequestEvent {
    data class Success(val loan: Loan) : LoanRequestEvent()
    data class Error(val message: String) : LoanRequestEvent()
    object Loading : LoanRequestEvent()
}

