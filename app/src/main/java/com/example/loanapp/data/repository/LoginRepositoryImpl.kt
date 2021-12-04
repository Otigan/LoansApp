package com.example.loanapp.data.repository

import com.example.loanapp.data.local.LoginDataStorePref
import com.example.loanapp.data.local.db.UserDao
import com.example.loanapp.data.local.model.UserEntityMapper
import com.example.loanapp.data.remote.data_source.LoginDataSource
import com.example.loanapp.data.remote.mapper.AuthTokenDtoMapper
import com.example.loanapp.data.remote.model.AuthTokenDto
import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.domain.entity.AuthToken
import com.example.loanapp.domain.entity.ResponseType
import com.example.loanapp.domain.entity.StateResponse
import com.example.loanapp.domain.entity.User
import com.example.loanapp.domain.repository.LoginRepository
import com.example.loanapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
    private val userDao: UserDao,
    private val loginDataStorePref: LoginDataStorePref
) :
    LoginRepository {

    override suspend fun login(loginRequestBody: LoginRequestBody): Flow<Resource<AuthToken>> =
        loginDataSource.login(loginRequestBody)
            .map { response ->
                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.let { token ->
                        saveAuthToken(token, loginRequestBody.name)
                        token.let {
                            Resource.success(
                                AuthTokenDtoMapper().mapToDomainModel(AuthTokenDto(it))
                            )
                        }
                    } ?: returnUnknownError()
                } else {
                    response.errorBody()?.let { responseBody ->
                        val errorMessage =
                            JSONObject(responseBody.charStream().readText()).getString("error")
                        Resource.error(
                            errorMessage,
                            AuthToken(
                                errorResponse = StateResponse(
                                    message = errorMessage,
                                    errorResponseType = ResponseType.Dialog
                                )
                            )
                        )
                    } ?: returnUnknownError()
                }
            }

    suspend fun checkPreviousAuthUser(): Flow<Resource<User>> {
        return loginDataStorePref.preferencesFlow
            .map { login ->
                if (login.isBlank()) {
                    returnNoTokenFound()
                }
                login.let {
                    userDao.searchByUserName(it).let { userEntity ->
                        if (userEntity.token != null) {
                            Resource.success(
                                UserEntityMapper().mapToDomainModel(userEntity)
                            )
                        } else {
                            returnNoTokenFound()
                        }
                    }
                }
            }
    }

    private suspend fun saveAuthToken(token: String?, name: String) {
        userDao.insertToken(token!!, name)
    }

    private fun returnNoTokenFound(): Resource<User> {
        return Resource.error(
            "No token was found",
            User(
                errorResponse = StateResponse(
                    "No token was found",
                    errorResponseType = ResponseType.None
                )
            )
        )
    }

    private fun returnUnknownError(): Resource<AuthToken> {
        return Resource.error(
            "Unknown error",
            AuthToken(
                errorResponse = StateResponse(
                    "Unknown error",
                    errorResponseType = ResponseType.Toast
                )
            )
        )
    }

}