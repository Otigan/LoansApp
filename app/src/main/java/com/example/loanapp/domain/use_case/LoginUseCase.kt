package com.example.loanapp.domain.use_case

import com.example.loanapp.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(name: String, password: String): String =
        loginRepository.login(name, password)

}