package com.example.loanapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.domain.use_case.auth.RegisterUseCase
import com.example.loanapp.util.AuthEvent
import com.example.loanapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) :
    ViewModel() {

    private val userChannel = Channel<AuthEvent>()
    val userFlow = userChannel.receiveAsFlow()

    fun register(name: String, password: String) = viewModelScope.launch {

        val resource = registerUseCase(
            RegisterRequestBody(
                name = name,
                password = password
            )
        )
        when (resource) {
            is Resource.Error -> {
                userChannel.send(AuthEvent.Error(resource.errorMessage!!))
            }
            is Resource.Success -> {
                userChannel.send(AuthEvent.Success)
            }
            is Resource.Loading -> TODO()
        }
    }
}