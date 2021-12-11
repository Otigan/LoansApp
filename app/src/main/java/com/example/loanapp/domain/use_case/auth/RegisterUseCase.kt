package com.example.loanapp.domain.use_case.auth

import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.domain.entity.User
import com.example.loanapp.domain.repository.auth.RegisterRepository
import com.example.loanapp.util.Resource
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val registerRepository: RegisterRepository) {

    suspend operator fun invoke(registerRequestBody: RegisterRequestBody): Resource<User> =
        registerRepository.register(registerRequestBody)

}