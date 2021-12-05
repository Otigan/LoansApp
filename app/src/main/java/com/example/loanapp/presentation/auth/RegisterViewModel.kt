package com.example.loanapp.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.domain.entity.User
import com.example.loanapp.domain.use_case.RegisterUseCase
import com.example.loanapp.util.Event
import com.example.loanapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) :
    ViewModel() {

    private val _user = MutableLiveData<Event<Resource<User>>>()
    val user: LiveData<Event<Resource<User>>>
        get() = _user

    fun register(name: String, password: String) = viewModelScope.launch {

        _user.postValue(Event(Resource.loading(null)))

        registerUseCase(
            RegisterRequestBody(
                name = name,
                password = password
            )
        ).collect {
            _user.postValue(Event(it))
        }
    }
}