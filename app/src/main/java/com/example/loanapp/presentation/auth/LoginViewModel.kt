package com.example.loanapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.domain.use_case.auth.LoginUseCase
import com.example.loanapp.domain.use_case.auth.TokenUseCase
import com.example.loanapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class Event {

    data class Success(val message: String) : Event()
    data class Error(val message: String) : Event()
    data class Loading(val message: String) : Event()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val tokenUseCase: TokenUseCase
) : ViewModel() {

    private val loginEventChannel = Channel<Event>()
    val loginEventFlow = loginEventChannel.receiveAsFlow()

    fun loginFromToken() = viewModelScope.launch {
        tokenUseCase.getSavedToken().collect { token ->
            if (token.isNotBlank()) {
                loginEventChannel.send(Event.Success("from token"))
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
                loginEventChannel.send(Event.Error(resource.errorMessage!!))
            }
            is Resource.Loading -> {
                loginEventChannel.send(Event.Loading(""))
            }
            is Resource.Success -> {
                saveToken(resource.data!!)
            }
        }
    }

    private suspend fun saveToken(token: String) = tokenUseCase.saveToken(token)
}
