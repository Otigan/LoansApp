package com.example.loanapp.data.api

import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.data.remote.model.UserDto
import retrofit2.Response
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
        @Body registerRequestBody: RegisterRequestBody
    ): Response<UserDto>

    @Headers(
        "Content-Type: application/json",
        "accept: */*"
    )
    @POST("login")
    suspend fun login(
        @Body loginRequestBody: LoginRequestBody
    ): Response<String>

}