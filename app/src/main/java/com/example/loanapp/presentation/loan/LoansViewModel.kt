package com.example.loanapp.presentation.loan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.use_case.loan.GetAllLoansUseCase
import com.example.loanapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoanEvent {

    data class Success(val loans: List<Loan>) : LoanEvent()
    data class ShowSnackbar(val message: String, val data: List<Loan>) : LoanEvent()
    data class ShowProgressBar(val message: String) : LoanEvent()
}

@HiltViewModel
class LoansViewModel @Inject constructor(
    private val getAllLoansUseCase: GetAllLoansUseCase,
) : ViewModel() {

    private val loansEventChannel = Channel<LoanEvent>()
    val loansEventFlow = loansEventChannel.receiveAsFlow()


    fun getAllLoans() =
        viewModelScope.launch {
            getAllLoansUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { loans ->
                            loansEventChannel.send(LoanEvent.Success(loans))
                        }
                    }
                    is Resource.Error -> {
                        resource.errorMessage?.let { errorMessage ->
                            resource.data?.let { loans ->
                                loansEventChannel.send(
                                    LoanEvent.ShowSnackbar(
                                        errorMessage,
                                        loans
                                    )
                                )
                            }
                        }
                    }
                    is Resource.Loading -> {
                        loansEventChannel.send(LoanEvent.ShowProgressBar(""))
                    }
                }
            }
        }

}