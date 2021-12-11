package com.example.loanapp.presentation.loan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.LoanRequestBody
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.entity.LoanCondition
import com.example.loanapp.domain.use_case.auth.TokenUseCase
import com.example.loanapp.domain.use_case.loan.GetLoanConditionUseCase
import com.example.loanapp.domain.use_case.loan.RequestLoanUseCase
import com.example.loanapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class LoanConditionEvent {
    data class LoanConditionSuccess(val loanCondition: LoanCondition) : LoanConditionEvent()
    data class ShowSnackBar(val message: String) : LoanConditionEvent()
}

sealed class LoanRequestEvent {
    data class Success(val loan: Loan) : LoanRequestEvent()
    data class ShowSnackbar(val message: String) : LoanRequestEvent()
    data class ShowProgressBar(val message: String) : LoanRequestEvent()
}

@HiltViewModel
class RequestLoanViewModel @Inject constructor(
    private val getLoanConditionUseCase: GetLoanConditionUseCase,
    private val requestLoanUseCase: RequestLoanUseCase,
    private val tokenUseCase: TokenUseCase
) :
    ViewModel() {

    private val loanConditionChannel = Channel<LoanConditionEvent>()
    val loanConditionFlow = loanConditionChannel.receiveAsFlow()

    private val loanChannel = Channel<LoanRequestEvent>()
    val loanFlow = loanChannel.receiveAsFlow()

    fun requestLoan(loanRequestBody: LoanRequestBody) {
        viewModelScope.launch {
            tokenUseCase.getSavedToken().collect { token ->
                when (val resource = requestLoanUseCase.invoke(token, loanRequestBody)) {
                    is Resource.Error -> {
                        loanChannel.send(LoanRequestEvent.ShowSnackbar(resource.errorMessage!!))
                    }
                    is Resource.Loading -> TODO()
                    is Resource.Success -> {
                        loanChannel.send(LoanRequestEvent.Success(resource.data!!))
                    }
                }
            }
        }
    }

    fun getLoanCondition() {
        viewModelScope.launch {
            tokenUseCase.getSavedToken().collect { token ->
                when (val resource = getLoanConditionUseCase(token)) {
                    is Resource.Error -> {
                        loanConditionChannel.send(LoanConditionEvent.ShowSnackBar(resource.errorMessage!!))
                    }
                    is Resource.Loading -> TODO()
                    is Resource.Success -> {
                        loanConditionChannel.send(LoanConditionEvent.LoanConditionSuccess(resource.data!!))
                    }
                }
            }
        }
    }
}