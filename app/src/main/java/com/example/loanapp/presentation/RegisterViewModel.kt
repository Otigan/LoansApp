package com.example.loanapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.domain.entity.User
import com.example.loanapp.domain.use_case.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) :
    ViewModel() {


    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun register(name: String, password: String) = viewModelScope.launch {
        val user = registerUseCase(name, password)
        _user.postValue(user)
    }
}