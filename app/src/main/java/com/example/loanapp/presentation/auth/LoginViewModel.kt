package com.example.loanapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.data.remote.util.Resource
import com.example.loanapp.domain.use_case.auth.GetTokenUseCase
import com.example.loanapp.domain.use_case.auth.LoginUseCase
import com.example.loanapp.domain.use_case.auth.RemoveTokenUseCase
import com.example.loanapp.domain.use_case.auth.SaveTokenUseCase
import com.example.loanapp.util.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val removeTokenUseCase: RemoveTokenUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    private val loginEventChannel = Channel<LoginEvent>(Channel.BUFFERED)
    val loginEventFlow = loginEventChannel.receiveAsFlow()


    fun checkToken() = viewModelScope.launch(Dispatchers.IO) {
        getTokenUseCase().collect { token ->
            if (token.isNotBlank()) {
                loginEventChannel.send(LoginEvent.Success)
            }
        }
    }

    fun login(name: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
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

    fun logout() = viewModelScope.launch(Dispatchers.IO) {
        removeTokenUseCase()
        loginEventChannel.send(LoginEvent.Logout)
    }

    private suspend fun saveToken(token: String) = saveTokenUseCase(token)
}
