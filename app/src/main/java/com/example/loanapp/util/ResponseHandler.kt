package com.example.loanapp.util

import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}

@Singleton
open class ResponseHandler @Inject constructor() {

    fun <T> handleSuccess(data: T): Resource<T> {
        return Resource.Success(data)
    }

    fun <T> handleException(e: Exception, data: T?): Resource<T> {
        e.printStackTrace()
        return when (e) {
            is HttpException -> Resource.Error(getErrorMessage(e.code()), data)
            is SocketTimeoutException -> Resource.Error(
                getErrorMessage(ErrorCodes.SocketTimeOut.code),
                data
            )
            else -> Resource.Error(getErrorMessage(Int.MIN_VALUE), data)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            401 -> "Unauthorised"
            403 -> "Banned"
            404 -> "Not found"
            else -> "Something went wrong"
        }
    }
}