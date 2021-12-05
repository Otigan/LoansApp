package com.example.loanapp.domain.use_case

import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.domain.entity.User
import com.example.loanapp.domain.repository.LoginRepository
import com.example.loanapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(loginRequestBody: LoginRequestBody): Flow<Resource<User>> =
        loginRepository.login(loginRequestBody)

}