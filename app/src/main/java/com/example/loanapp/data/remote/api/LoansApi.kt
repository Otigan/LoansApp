package com.example.loanapp.data.remote.api

import com.example.loanapp.data.remote.model.*
import com.example.loanapp.domain.entity.LoanCondition
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoansApi {


    @POST("registration")
    suspend fun register(
        @Body registerRequestBody: RegisterRequestBody
    ): UserDto

    @POST("login")
    suspend fun login(
        @Body loginRequestBody: LoginRequestBody
    ): String


    @GET("loans/all")
    suspend fun getAllLoans(): List<LoanDto>


    @POST("loans")
    suspend fun requestLoan(
        @Body loanRequestBody: LoanRequestBody
    ): LoanDto

    @GET("loans/conditions")
    suspend fun getLoanCondition(
    ): LoanCondition
}