package com.example.loanapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.util.Resource
import com.example.loanapp.domain.use_case.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) :
    ViewModel() {

    private val userChannel = Channel<Event>()
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
                    userChannel.send(Event.Error(resource.errorMessage!!))
                }
                is Resource.Success -> {
                    userChannel.send(Event.Success("success"))
                }
                is Resource.Loading -> TODO()
            }
        }
    }
}