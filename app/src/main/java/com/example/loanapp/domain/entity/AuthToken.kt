package com.example.loanapp.domain.entity

data class AuthToken(
    val pk: Int? = null,
    val token: String? = null,
    val error: String? = null,
    val errorResponse: StateResponse? = null
)

data class StateResponse(
    val message: String,
    val errorResponseType: ResponseType
)


sealed class ResponseType {

    object Toast : ResponseType()

    object Dialog : ResponseType()

    object None : ResponseType()

}