package com.example.loanapp.data.repository

import com.example.loanapp.data.local.db.UserDao
import com.example.loanapp.data.local.model.UserEntity
import com.example.loanapp.data.remote.data_source.auth.RegisterDataSource
import com.example.loanapp.data.remote.mapper.UserDtoMapper
import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.data.remote.model.UserDto
import com.example.loanapp.domain.entity.ResponseType
import com.example.loanapp.domain.entity.StateResponse
import com.example.loanapp.domain.entity.User
import com.example.loanapp.domain.repository.RegisterRepository
import com.example.loanapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerDataSource: RegisterDataSource,
    private val userDao: UserDao
) :
    RegisterRepository {

    override suspend fun register(registerRequestBody: RegisterRequestBody): Flow<Resource<User>> =
        registerDataSource.register(registerRequestBody)
            .map { response ->
                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.let { userDto ->
                        saveUser(userDto)
                        userDto.let {
                            Resource.success(
                                UserDtoMapper().mapToDomainModel(it)
                            )
                        }
                    } ?: returnUnknownError()
                } else {
                    response.errorBody()?.let { responseBody ->
                        val errorMessage =
                            JSONObject(responseBody.charStream().readText()).getString("error")
                        Resource.error(
                            errorMessage,
                            User(
                                errorResponse = StateResponse(
                                    message = errorMessage,
                                    errorResponseType = ResponseType.Dialog
                                )
                            )
                        )
                    } ?: returnUnknownError()
                }
            }

    private suspend fun saveUser(userDto: UserDto) {
        userDao.insert(
            UserEntity(
                name = userDto.name,
                role = userDto.role
            )
        )
    }


    private fun returnUnknownError(): Resource<User> {
        return Resource.error(
            "Unknown error",
            User(
                errorResponse = StateResponse(
                    "Unknown error",
                    errorResponseType = ResponseType.Toast
                )
            )
        )
    }

}