package com.example.loanapp.data.api

import com.example.loanapp.data.LoginBody
import com.example.loanapp.domain.entity.User
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoansApi {

    companion object {
        const val BASE_URL = "http://focusstart.appspot.com/"
    }

    @Headers(
        "Content-Type: application/json",
        "accept: */*"
    )
    @POST("registration")
    suspend fun register(
        @Body registerBody: LoginBody
    ): User

    @Headers(
        "Content-Type: application/json",
        "accept: */*"
    )
    @POST("login")
    suspend fun login(
        @Body loginBody: LoginBody
    ): String

}