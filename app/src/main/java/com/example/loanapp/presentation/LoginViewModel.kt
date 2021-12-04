package com.example.loanapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.domain.entity.AuthToken
import com.example.loanapp.domain.use_case.LoginUseCase
import com.example.loanapp.util.Event
import com.example.loanapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {


    private val _loginResult = MutableLiveData<Event<Resource<AuthToken>>>()
    val loginResult: LiveData<Event<Resource<AuthToken>>>
        get() = _loginResult


    fun login(name: String, password: String) = viewModelScope.launch {

        _loginResult.postValue(Event(Resource.loading(null)))
        loginUseCase(
            LoginRequestBody(
                name = name,
                password = password
            )
        ).collect {
            _loginResult.postValue(Event(it))
        }
    }
}