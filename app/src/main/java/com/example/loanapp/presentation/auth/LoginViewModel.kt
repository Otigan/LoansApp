package com.example.loanapp.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.domain.use_case.auth.LoginUseCase
import com.example.loanapp.domain.use_case.auth.TokenUseCase
import com.example.loanapp.util.LoginEvent
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

    private val loginEventChannel = Channel<LoginEvent>()
    val loginEventFlow = loginEventChannel.receiveAsFlow()


    fun checkToken() = viewModelScope.launch {
        tokenUseCase.getSavedToken().collect { token ->
            if (token.isNotBlank()) {
                loginEventChannel.send(LoginEvent.Success)
            } else {
                loginEventChannel.send(LoginEvent.Logout)
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
                resource.errorMessage?.let { errorMessage ->
                    loginEventChannel.send(LoginEvent.Error(errorMessage))
                }
            }
            is Resource.Loading -> {
                loginEventChannel.send(LoginEvent.Loading)
            }
            is Resource.Success -> {
                resource.data?.let { token ->
                    saveToken(token)
                }
            }
        }
    }

    fun logout() = viewModelScope.launch {
        tokenUseCase.removeToken()
        checkToken()
    }

    private suspend fun saveToken(token: String) = tokenUseCase.saveToken(token)
}
