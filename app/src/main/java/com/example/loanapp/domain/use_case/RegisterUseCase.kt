package com.example.loanapp.domain.use_case

import com.example.loanapp.domain.entity.User
import com.example.loanapp.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val registerRepository: RegisterRepository) {

    suspend operator fun invoke(name: String, password: String): User =
        registerRepository.register(name, password)

}