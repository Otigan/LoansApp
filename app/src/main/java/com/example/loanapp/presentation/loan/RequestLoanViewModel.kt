package com.example.loanapp.presentation.loan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.LoanRequestBody
import com.example.loanapp.domain.Resource
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.entity.LoanCondition
import com.example.loanapp.domain.use_case.auth.TokenUseCase
import com.example.loanapp.domain.use_case.loan.GetLoanConditionUseCase
import com.example.loanapp.domain.use_case.loan.RequestLoanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoanRequestEvent {
    data class LoanConditionSuccess(val loanCondition: LoanCondition) : LoanRequestEvent()
    data class Success(val loan: Loan) : LoanRequestEvent()
    data class ShowSnackbar(val message: String) : LoanRequestEvent()
}

@HiltViewModel
class RequestLoanViewModel @Inject constructor(
    private val getLoanConditionUseCase: GetLoanConditionUseCase,
    private val requestLoanUseCase: RequestLoanUseCase,
    private val tokenUseCase: TokenUseCase
) :
    ViewModel() {

    private val loanConditionChannel = Channel<LoanRequestEvent>()
    val loanConditionFlow = loanConditionChannel.receiveAsFlow()

    private val loanChannel = Channel<LoanRequestEvent>()
    val loanFlow = loanChannel.receiveAsFlow()

    fun requestLoan(loanRequestBody: LoanRequestBody) {
        viewModelScope.launch {
            tokenUseCase.getSavedToken().collect { token ->
                requestLoanUseCase.invoke(token, loanRequestBody).collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            loanChannel.send(LoanRequestEvent.Success(resource.data!!))
                        }
                        is Resource.Error -> {
                            loanChannel.send(LoanRequestEvent.ShowSnackbar(resource.errorMessage!!))
                        }
                        is Resource.Loading -> TODO()
                    }
                }
            }
        }
    }

    fun getLoanCondition() {
        viewModelScope.launch {
            tokenUseCase.getSavedToken().collect { token ->
                getLoanConditionUseCase(token).collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            loanConditionChannel.send(LoanRequestEvent.ShowSnackbar(resource.errorMessage!!))
                        }
                        is Resource.Success -> {
                            loanConditionChannel.send(LoanRequestEvent.LoanConditionSuccess(resource.data!!))
                        }
                        is Resource.Loading -> TODO()
                    }
                }
            }
        }
    }
}