package com.example.loanapp.data.remote.api

import com.example.loanapp.data.remote.model.*
import com.example.loanapp.domain.entity.LoanCondition
import retrofit2.http.*

interface LoansApi {

    companion object {
        const val BASE_URL = "https://focusstart.appspot.com/"
    }

    @Headers(
        "Content-Type: application/json",
        "accept: */*"
    )
    @POST("registration")
    suspend fun register(
        @Body registerRequestBody: RegisterRequestBody
    ): UserDto

    @Headers(
        "Content-Type: application/json",
        "accept: */*"
    )
    @POST("login")
    suspend fun login(
        @Body loginRequestBody: LoginRequestBody
    ): String


    @GET("loans/all")
    suspend fun getAllLoans(@Header("Authorization") token: String): List<LoanDto>


    @POST("loans")
    suspend fun requestLoan(
        @Header("Authorization") token: String,
        @Body loanRequestBody: LoanRequestBody
    ): LoanDto

    @GET("loans/conditions")
    suspend fun getLoanCondition(
        @Header("Authorization") token: String
    ): LoanCondition
}