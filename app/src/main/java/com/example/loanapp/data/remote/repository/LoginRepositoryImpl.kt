package com.example.loanapp.data.remote.repository

import com.example.loanapp.data.remote.data_source.auth.LoginDataSource
import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.domain.Resource
import com.example.loanapp.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
) : LoginRepository {

    override suspend fun login(loginRequestBody: LoginRequestBody): Flow<Resource<String>> =
        loginDataSource.login(loginRequestBody).map { response ->
            if (response.isSuccessful && response.code() == 200) {
                val token = response.body()!!
                Resource.Success(token)
            } else {
                when (response.code()) {
                    401 -> {
                        Resource.Error("Пользователь не авторизован")
                    }
                    403 -> {
                        Resource.Error("Forbidden")
                    }
                    404 -> {
                        Resource.Error("Аккаунт не найден")
                    }
                    else -> {
                        Resource.Error("Произошла неизвестная ошибка 2")
                    }
                }
            }
        }
}