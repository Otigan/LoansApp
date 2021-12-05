package com.example.loanapp.data.api

import com.example.loanapp.data.remote.model.*
import com.example.loanapp.domain.entity.LoanCondition
import retrofit2.Response
import retrofit2.http.*

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


    @GET("loans/all")
    suspend fun getAllLoans(@Header("Authorization") token: String): Response<List<LoanDto>>


    @POST("loans")
    suspend fun requestLoan(
        @Header("Authorization") token: String,
        @Body loanRequestBody: LoanRequestBody
    ): Response<LoanDto>

    @GET("loans/conditions")
    suspend fun getLoanCondition(
        @Header("Authorization") token: String
    ): Response<LoanCondition>
}