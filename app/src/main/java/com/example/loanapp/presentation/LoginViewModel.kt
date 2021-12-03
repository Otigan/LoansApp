package com.example.loanapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.domain.use_case.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {


    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token


    fun login(name: String, password: String) = viewModelScope.launch {
        val token = loginUseCase(name, password)
        _token.postValue(token)
    }

}