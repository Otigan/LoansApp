package com.example.loanapp.data.remote.repository

import com.example.loanapp.data.remote.data_source.auth.RegisterDataSource
import com.example.loanapp.data.remote.mapper.UserDtoMapper
import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.domain.entity.User
import com.example.loanapp.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerDataSource: RegisterDataSource,
) : RegisterRepository {

    override suspend fun register(registerRequestBody: RegisterRequestBody): Flow<Response<User>> =
        registerDataSource.register(registerRequestBody).map { response ->
            if (response.isSuccessful && response.code() == 200) {
                response.body()?.let { userDto ->
                    val user = UserDtoMapper().mapToDomainModel(userDto)
                    user.password = registerRequestBody.password
                    Response.success(
                        user
                    )
                }!!
            } else {
                Response.success(User())
            }
        }
}