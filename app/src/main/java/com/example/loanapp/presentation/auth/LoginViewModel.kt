package com.example.loanapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.domain.Resource
import com.example.loanapp.domain.use_case.auth.LoginUseCase
import com.example.loanapp.domain.use_case.auth.TokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthEvent {

    data class Success(val message: String) : AuthEvent()
    data class ShowSnackbar(val message: String) : AuthEvent()
}

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
                loginEventChannel.send(AuthEvent.Success("from token"))
            }
        }
    }

    fun login(name: String, password: String) = viewModelScope.launch {
        loginUseCase(
            LoginRequestBody(
                name = name,
                password = password
            )
        ).collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    loginEventChannel.send(AuthEvent.ShowSnackbar(resource.errorMessage!!))
                }
                is Resource.Success -> {
                    saveToken(resource.data!!)
                }
                is Resource.Loading -> TODO()
            }
        }
    }

    private suspend fun saveToken(token: String) = tokenUseCase.saveToken(token)
}
