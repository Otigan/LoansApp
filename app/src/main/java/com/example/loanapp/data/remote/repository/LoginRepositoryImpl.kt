package com.example.loanapp.data.remote.repository

import com.example.loanapp.data.remote.data_source.auth.LoginDataSource
import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.data.remote.util.AuthErrorHandler
import com.example.loanapp.domain.repository.LoginRepository
import com.example.loanapp.util.Resource
import com.example.loanapp.util.ResponseHandler
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
    private val authErrorHandler: AuthErrorHandler
) : LoginRepository {

    override suspend fun login(loginRequestBody: LoginRequestBody): Resource<String> {
        val responseHandler = ResponseHandler()
        return try {
            val response = loginDataSource.login(loginRequestBody)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e, null)
        }
    }

    /*override suspend fun login(loginRequestBody: LoginRequestBody): Flow<Resource<String>> =
        loginDataSource.login(loginRequestBody).map { response ->
            if (response.isSuccessful && response.code() == 200 || response.code() == 201) {
                response.body()?.let { token ->
                    Resource.Success(token)
                } ?: returnUnknownError()
            } else {
                val errorMessage = authErrorHandler(response.code())
                Resource.Error(errorMessage = errorMessage)
            }
        }*/
}