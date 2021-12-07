package com.example.loanapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.domain.Resource
import com.example.loanapp.domain.use_case.auth.RegisterUseCase
import com.example.loanapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RegisterEvent {

    data class Success(val message: String) : RegisterEvent()
    data class ShowSnackbar(val message: String) : RegisterEvent()
}

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) :
    ViewModel() {

    private val userChannel = Channel<RegisterEvent>()
    val userFlow = userChannel.receiveAsFlow()

    fun register(name: String, password: String) = viewModelScope.launch {

        registerUseCase(
            RegisterRequestBody(
                name = name,
                password = password
            )
        ).collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    userChannel.send(RegisterEvent.ShowSnackbar(resource.errorMessage!!))
                }
                is Resource.Success -> {
                    userChannel.send(RegisterEvent.Success("success"))
                }
                is Resource.Loading -> TODO()
            }
        }
    }
}