package com.example.loanapp.presentation.loan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.LoanDto
import com.example.loanapp.data.remote.model.LoanRequestBody
import com.example.loanapp.domain.entity.LoanCondition
import com.example.loanapp.domain.use_case.GetLoanConditionUseCase
import com.example.loanapp.domain.use_case.RequestLoanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestLoanViewModel @Inject constructor(
    private val getLoanConditionUseCase: GetLoanConditionUseCase,
    private val requestLoanUseCase: RequestLoanUseCase
) :
    ViewModel() {

    private val _loanCondition = MutableLiveData<LoanCondition>()
    val loanCondition: LiveData<LoanCondition> get() = _loanCondition

    private val _loan = MutableLiveData<LoanDto>()
    val loan: LiveData<LoanDto> get() = _loan

    fun requestLoan(name: Flow<String>, loanRequestBody: LoanRequestBody) {
        viewModelScope.launch {
            name.collect { name ->
                requestLoanUseCase.invoke(name, loanRequestBody)
                    .collect { response ->
                        if (response.isSuccessful && response.code() == 200) {
                            response.body()?.let { loanDto ->
                                _loan.postValue(loanDto)
                            }
                        } else {
                            Log.d("RequestLoanViewModel", "requestLoan: ${response.code()}")
                        }
                    }
            }
        }
    }

    fun getLoanCondition(name: Flow<String>) {
        viewModelScope.launch {
            name.collect {
                getLoanConditionUseCase(it).collect { response ->
                    response.body()?.let { loanCondition ->
                        _loanCondition.postValue(loanCondition)
                    }
                }
            }
        }
    }
}