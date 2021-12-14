package com.example.loanapp.data.repository

import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.data.remote.util.Resource
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response


fun codeToErrorMessage(code: Int): String =
    if (code == 404) {
        "Пользователь не найден"
    } else {
        "Неизвестная ошибка"
    }

class FakeLoginDataSource {

    private val correctLoginRequestBody = LoginRequestBody("John", "sample")

    suspend fun login(loginRequestBody: LoginRequestBody): Response<String> {
        if (loginRequestBody !== correctLoginRequestBody) {
            val response = Response.error<String>(
                404,
                ResponseBody.create(MediaType.parse("application/json"), "")
            )
            throw HttpException(response)
        } else {
            return Response.success(200, "token")
        }
    }
}

class FakeLoginRepository(private val loginDataSource: FakeLoginDataSource) {

    suspend fun login(loginRequestBody: LoginRequestBody): Resource<String> {
        return try {
            val response = loginDataSource.login(loginRequestBody)
            Resource.Success(response.body()!!)
        } catch (e: Exception) {
            if (e is HttpException) {
                Resource.Error(
                    codeToErrorMessage(e.code()),
                    "Пользователь не найден"
                )
            } else Resource.Error("Неизвестная ошибка")
        }
    }
}