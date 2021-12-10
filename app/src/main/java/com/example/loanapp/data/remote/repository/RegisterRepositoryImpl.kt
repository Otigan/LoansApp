package com.example.loanapp.data.remote.repository

import com.example.loanapp.data.remote.data_source.auth.RegisterDataSource
import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.data.remote.model.UserDto
import com.example.loanapp.data.remote.util.AuthErrorHandler
import com.example.loanapp.di.UserMapper
import com.example.loanapp.domain.entity.User
import com.example.loanapp.domain.repository.RegisterRepository
import com.example.loanapp.domain.util.Mapper
import com.example.loanapp.util.Common.returnUnknownError
import com.example.loanapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerDataSource: RegisterDataSource,
    @UserMapper private val userDtoMapper: Mapper<UserDto, User>,
    private val authErrorHandler: AuthErrorHandler
) : RegisterRepository {

    override suspend fun register(registerRequestBody: RegisterRequestBody): Flow<Resource<User>> =
        registerDataSource.register(registerRequestBody).map { response ->
            if (response.isSuccessful && response.code() == 200 || response.code() == 201) {
                response.body()?.let { userDto ->
                    Resource.Success(userDtoMapper.mapToDomainModel(userDto))
                } ?: returnUnknownError()
            } else {
                val errorMessage = authErrorHandler(response.code())
                Resource.Error(errorMessage = errorMessage)
            }
        }
}