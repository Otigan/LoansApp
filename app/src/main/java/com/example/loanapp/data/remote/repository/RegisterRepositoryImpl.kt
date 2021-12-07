package com.example.loanapp.data.remote.repository

import com.example.loanapp.data.remote.data_source.auth.RegisterDataSource
import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.data.remote.model.UserDto
import com.example.loanapp.di.UserMapper
import com.example.loanapp.domain.Resource
import com.example.loanapp.domain.entity.User
import com.example.loanapp.domain.repository.RegisterRepository
import com.example.loanapp.domain.util.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerDataSource: RegisterDataSource,
    @UserMapper private val userDtoMapper: Mapper<UserDto, User>
) : RegisterRepository {

    override suspend fun register(registerRequestBody: RegisterRequestBody): Flow<Resource<User>> =
        registerDataSource.register(registerRequestBody).map { response ->
            if (response.isSuccessful && response.code() == 200) {
                response.body()?.let { userDto ->
                    Resource.Success(userDtoMapper.mapToDomainModel(userDto))
                } ?: Resource.Error("Неизвестная ошибка")
            } else {
                when (response.code()) {
                    401 -> {
                        Resource.Error("Пользователь не авторизован")
                    }
                    403 -> {
                        Resource.Error("Forbidden")
                    }
                    404 -> {
                        Resource.Error("Not found")
                    }
                    else -> {
                        Resource.Error("Произошла неизвестная ошибка 2")
                    }
                }
            }
        }
}