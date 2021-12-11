package com.example.loanapp.util

import com.example.loanapp.domain.entity.Loan

object Common {

    fun <T> returnUnknownError(): Resource.Error<T> =
        Resource.Error("Произошла неизвестная ошибка")

    fun sortLoansByDescending(list: List<Loan>): List<Loan> = list.sortedByDescending { loan ->
        loan.id
    }

}