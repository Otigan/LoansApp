package com.example.loanapp.data.remote.repository

import com.example.loanapp.data.remote.data_source.auth.LoginDataSource
import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.domain.Resource
import com.example.loanapp.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) :
    LoginRepository {

    override suspend fun login(loginRequestBody: LoginRequestBody): Flow<Resource<String>> =
        loginDataSource.login(loginRequestBody).map { response ->
            if (response.isSuccessful && response.code() == 200) {
                response.body()?.let { token ->
                    Resource.Success(token)
                } ?: Resource.Error("Произошла неизвестная ошибка")
            } else {
                val errorCode = response.code()
                if (errorCode == 404) {
                    Resource.Error("Аккаунт не найден")
                } else {
                    Resource.Error("Произошла неизвестная ошибка")
                }
            }
        }

}