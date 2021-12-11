package com.example.loanapp.domain.use_case.auth

import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.domain.repository.auth.LoginRepository
import com.example.loanapp.util.Resource
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(loginRequestBody: LoginRequestBody): Resource<String> =
        loginRepository.login(loginRequestBody)
}