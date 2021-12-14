package com.example.loanapp.util

import com.example.loanapp.domain.entity.Loan

object Common {

    const val BASE_URL = "https://focusstart.appspot.com/"
    const val DATASTORE_NAME = "LOAN_DATASTORE"

    fun sortLoansByDescending(list: List<Loan>): List<Loan> = list.sortedByDescending { loan ->
        loan.id
    }

}