package com.example.loanapp.util

import com.example.loanapp.domain.entity.Loan

object Common {

    const val LANGUAGE_PREFS = "PREFERENCES_LANGUAGE"
    const val SELECTED_LANGUAGE = "lang"

    fun sortLoansByDescending(list: List<Loan>): List<Loan> = list.sortedByDescending { loan ->
        loan.id
    }

}