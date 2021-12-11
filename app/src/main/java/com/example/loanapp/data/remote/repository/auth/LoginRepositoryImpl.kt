package com.example.loanapp.data.remote.repository.auth

import com.example.loanapp.data.remote.data_source.auth.LoginDataSource
import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.domain.repository.auth.LoginRepository
import com.example.loanapp.util.Resource
import com.example.loanapp.util.ResponseHandler
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
    private val responseHandler: ResponseHandler
) : LoginRepository {

    override suspend fun login(loginRequestBody: LoginRequestBody): Resource<String> {
        return try {
            val response = loginDataSource.login(loginRequestBody)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e, null)
        }
    }
}