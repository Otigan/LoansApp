package com.example.loanapp.data.remote.repository.auth

import com.example.loanapp.data.remote.data_source.auth.RegisterDataSource
import com.example.loanapp.data.remote.mapper.UserMapper
import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.domain.entity.User
import com.example.loanapp.domain.repository.auth.RegisterRepository
import com.example.loanapp.util.Resource
import com.example.loanapp.util.ResponseHandler
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerDataSource: RegisterDataSource,
    private val responseHandler: ResponseHandler,
    private val userMapper: UserMapper
) : RegisterRepository {

    override suspend fun register(registerRequestBody: RegisterRequestBody): Resource<User> {
        return try {
            val response = registerDataSource.register(registerRequestBody)
            val user = userMapper.mapUserDtoToUser(response)
            return responseHandler.handleSuccess(user)
        } catch (e: Exception) {
            responseHandler.handleException(e, null)
        }
    }

}