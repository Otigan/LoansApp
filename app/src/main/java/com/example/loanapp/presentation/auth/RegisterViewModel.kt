package com.example.loanapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.data.remote.util.Resource
import com.example.loanapp.domain.use_case.auth.RegisterUseCase
import com.example.loanapp.util.RegisterEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) :
    ViewModel() {

    private val userChannel = Channel<RegisterEvent>(Channel.BUFFERED)
    val userFlow = userChannel.receiveAsFlow()

    fun register(name: String, password: String) = viewModelScope.launch(Dispatchers.IO) {

        val resource = registerUseCase(
            RegisterRequestBody(
                name = name,
                password = password
            )
        )
        when (resource) {
            is Resource.Error -> {
                resource.errorMessage?.let { errorMessage ->
                    userChannel.send(RegisterEvent.Error(errorMessage))
                }
            }
            is Resource.Success -> {
                userChannel.send(RegisterEvent.Success)
            }
            is Resource.Loading -> {
                userChannel.send(RegisterEvent.Loading)
            }
        }
    }
}