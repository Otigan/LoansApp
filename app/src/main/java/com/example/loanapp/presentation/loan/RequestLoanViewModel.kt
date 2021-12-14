package com.example.loanapp.presentation.loan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.data.remote.model.LoanRequestBody
import com.example.loanapp.data.remote.util.Resource
import com.example.loanapp.domain.use_case.loan.GetLoanConditionUseCase
import com.example.loanapp.domain.use_case.loan.RequestLoanUseCase
import com.example.loanapp.util.LoanConditionEvent
import com.example.loanapp.util.LoanRequestEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RequestLoanViewModel @Inject constructor(
    private val getLoanConditionUseCase: GetLoanConditionUseCase,
    private val requestLoanUseCase: RequestLoanUseCase,
) : ViewModel() {

    private val loanConditionChannel = Channel<LoanConditionEvent>(Channel.BUFFERED)
    val loanConditionFlow = loanConditionChannel.receiveAsFlow()

    private val loanChannel = Channel<LoanRequestEvent>(Channel.BUFFERED)
    val loanFlow = loanChannel.receiveAsFlow()

    fun requestLoan(loanRequestBody: LoanRequestBody) {
        viewModelScope.launch {
            when (val resource = requestLoanUseCase.invoke(loanRequestBody)) {
                is Resource.Error -> {
                    resource.errorMessage?.let { errorMessage ->
                        loanChannel.send(LoanRequestEvent.Error(errorMessage))
                    }
                }
                is Resource.Success -> {
                    resource.data?.let { loan ->
                        loanChannel.send(LoanRequestEvent.Success(loan))
                    }
                }
                is Resource.Loading -> {
                    loanChannel.send(LoanRequestEvent.Loading)
                }
            }
        }
    }

    fun getLoanCondition() {
        viewModelScope.launch {
            when (val resource = getLoanConditionUseCase()) {
                is Resource.Error -> {
                    resource.errorMessage?.let { errorMessage ->
                        loanConditionChannel.send(LoanConditionEvent.Error(errorMessage))
                    }
                }
                is Resource.Success -> {
                    resource.data?.let { loanCondition ->
                        loanConditionChannel.send(
                            LoanConditionEvent.Success(
                                loanCondition
                            )
                        )
                    }
                }
                is Resource.Loading -> {
                    loanConditionChannel.send(LoanConditionEvent.Loading)
                }
            }
        }
    }
}