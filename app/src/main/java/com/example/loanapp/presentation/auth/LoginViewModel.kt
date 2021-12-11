package com.example.loanapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.domain.use_case.auth.LoginUseCase
import com.example.loanapp.domain.use_case.auth.TokenUseCase
import com.example.loanapp.util.AuthEvent
import com.example.loanapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val tokenUseCase: TokenUseCase
) : ViewModel() {

    private val loginEventChannel = Channel<AuthEvent>()
    val loginEventFlow = loginEventChannel.receiveAsFlow()

    fun loginFromToken() = viewModelScope.launch {
        tokenUseCase.getSavedToken().collect { token ->
            if (token.isNotBlank()) {
                loginEventChannel.send(AuthEvent.Success)
            }
        }
    }

    fun login(name: String, password: String) = viewModelScope.launch {

        val resource = loginUseCase(
            LoginRequestBody(
                name = name,
                password = password
            )
        )
        when (resource) {
            is Resource.Error -> {
                loginEventChannel.send(AuthEvent.Error(resource.errorMessage!!))
            }
            is Resource.Loading -> {
                loginEventChannel.send(AuthEvent.Loading)
            }
            is Resource.Success -> {
                saveToken(resource.data!!)
            }
        }
    }


    private suspend fun saveToken(token: String) = tokenUseCase.saveToken(token)
}
