package com.example.loanapp.util

object Common {

    fun <T> returnUnknownError(): Resource.Error<T> =
        Resource.Error("Произошла неизвестная ошибка")


}