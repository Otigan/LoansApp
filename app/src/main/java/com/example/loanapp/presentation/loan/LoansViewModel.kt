package com.example.loanapp.presentation.loan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.domain.Resource
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.use_case.auth.TokenUseCase
import com.example.loanapp.domain.use_case.loan.GetAllLoansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoanEvent {

    data class Success(val loans: List<Loan>) : LoanEvent()
    data class ShowSnackbar(val message: String) : LoanEvent()
}

@HiltViewModel
class LoansViewModel @Inject constructor(
    private val getAllLoansUseCase: GetAllLoansUseCase,
    private val tokenUseCase: TokenUseCase
) :
    ViewModel() {

    private val loansEventChannel = Channel<LoanEvent>()
    val loansEventFlow = loansEventChannel.receiveAsFlow()


    fun getAllLoans() =
        viewModelScope.launch {
            tokenUseCase.getSavedToken().collect { token ->
                getAllLoansUseCase(token).collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            loansEventChannel.send(LoanEvent.Success(resource.data!!))
                        }
                        is Resource.Error -> {
                            loansEventChannel.send(LoanEvent.ShowSnackbar(resource.errorMessage!!))
                        }
                        is Resource.Loading -> TODO()
                    }
                }
            }
        }

}