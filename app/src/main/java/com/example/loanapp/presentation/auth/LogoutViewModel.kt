package com.example.loanapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.domain.use_case.auth.TokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LogoutViewModel @Inject constructor(private val tokenUseCase: TokenUseCase) : ViewModel() {



    fun logout() = viewModelScope.launch {
        //TODO: а почему не removeToken?
        tokenUseCase.saveToken(
            ""
        )
    }
}