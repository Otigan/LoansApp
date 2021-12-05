package com.example.loanapp.presentation.loan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.LoanDto
import com.example.loanapp.domain.use_case.GetAllLoansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoansViewModel @Inject constructor(private val getAllLoansUseCase: GetAllLoansUseCase) :
    ViewModel() {

    private val _loans = MutableLiveData<List<LoanDto>>()
    val loans get() = _loans


    fun getAllLoans(name: Flow<String>) =
        viewModelScope.launch {
            name.collect {
                getAllLoansUseCase(it).collect { response ->
                    response.body()?.let { loansDto ->
                        _loans.postValue(loansDto)
                        Log.d("LoansViewModel", "getAllLoans:${loansDto[0].id}")
                    }
                }
            }
        }

}