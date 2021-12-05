package com.example.loanapp.ui.loans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.loanapp.R
import com.example.loanapp.data.local.LoginDataStorePref
import com.example.loanapp.data.remote.model.LoanRequestBody
import com.example.loanapp.databinding.FragmentRequestLoanBinding
import com.example.loanapp.domain.entity.LoanCondition
import com.example.loanapp.presentation.loan.RequestLoanViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class RequestLoanFragment : Fragment(R.layout.fragment_request_loan) {

    private var _binding: FragmentRequestLoanBinding? = null
    private val binding get() = _binding!!
    private val requestLoanViewModel by viewModels<RequestLoanViewModel>()
    private lateinit var loanCondition: LoanCondition

    @Inject
    lateinit var loginDataStorePref: LoginDataStorePref


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestLoanBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name: Flow<String> = loginDataStorePref.loginDataStore.data.map { preferences ->
            preferences[LoginDataStorePref.PreferenceKey.name] ?: ""
        }

        requestLoanViewModel.getLoanCondition(name)

        requestLoanViewModel.loanCondition.observe(viewLifecycleOwner, {
            loanCondition = it
            Toast.makeText(
                context,
                "${it.maxAmount} ${it.percent}% ${it.period}",
                Toast.LENGTH_SHORT
            ).show()
        })

        requestLoanViewModel.loan.observe(viewLifecycleOwner, {
            Toast.makeText(context, it.state, Toast.LENGTH_SHORT).show()
        })


        binding.btnRequestLoan.setOnClickListener {
            val amount = binding.editTxtAmount.text.toString().toInt()
            val firstName = binding.editTxtFirstName.text.toString()
            val lastName = binding.editTxtLastName.text.toString()
            val percent = binding.editTxtPercent.text.toString().toDouble()
            val period = binding.editTxtPeriod.text.toString().toInt()
            val phoneNumber = binding.editTextPhoneNumber.text.toString()

            val loan = LoanRequestBody(amount, firstName, lastName, percent, period, phoneNumber)
            if (checkLoanCondition(loan, loanCondition)) {
                requestLoanViewModel.requestLoan(name, loan)
            }
        }

    }

    private fun checkLoanCondition(
        loanRequest: LoanRequestBody,
        condition: LoanCondition
    ): Boolean =
        when {
            loanRequest.amount > condition.maxAmount -> {
                false
            }
            loanRequest.percent > condition.percent -> {
                false
            }
            loanRequest.period < condition.period -> {
                false
            }
            else -> {
                true
            }
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}