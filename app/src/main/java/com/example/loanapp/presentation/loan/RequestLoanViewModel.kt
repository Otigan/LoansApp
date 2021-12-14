package com.example.loanapp.presentation.loan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.LoanRequestBody
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.entity.LoanCondition
import com.example.loanapp.domain.use_case.loan.GetLoanConditionUseCase
import com.example.loanapp.domain.use_case.loan.RequestLoanUseCase
import com.example.loanapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
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
) : ViewModel() {

    private val loanConditionChannel = Channel<LoanConditionEvent>()
    val loanConditionFlow = loanConditionChannel.receiveAsFlow()

    private val loanChannel = Channel<LoanRequestEvent>()
    val loanFlow = loanChannel.receiveAsFlow()

    fun requestLoan(loanRequestBody: LoanRequestBody) {
        viewModelScope.launch {
            when (val resource = requestLoanUseCase.invoke(loanRequestBody)) {
                is Resource.Error -> {
                    resource.errorMessage?.let { errorMessage ->
                        loanChannel.send(LoanRequestEvent.ShowSnackbar(errorMessage))
                    }
                }
                is Resource.Loading -> TODO() //TODO: не забудь убрать это а то свалиться с NotImplementedError
                is Resource.Success -> {
                    resource.data?.let { loan ->
                        loanChannel.send(LoanRequestEvent.Success(loan))
                    }
                }
            }
        }
    }

    fun getLoanCondition() {
        viewModelScope.launch {
            when (val resource = getLoanConditionUseCase()) {
                is Resource.Error -> {
                    resource.errorMessage?.let { errorMessage ->
                        loanConditionChannel.send(LoanConditionEvent.ShowSnackBar(errorMessage))
                    }
                }
                is Resource.Loading -> TODO() //TODO: не забудь убрать это а то свалиться с NotImplementedError
                is Resource.Success -> {
                    resource.data?.let { loanCondition ->
                        loanConditionChannel.send(
                            LoanConditionEvent.LoanConditionSuccess(
                                loanCondition
                            )
                        )
                    }
                }
            }
        }
    }
}