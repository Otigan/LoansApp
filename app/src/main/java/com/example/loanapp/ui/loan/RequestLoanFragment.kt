package com.example.loanapp.ui.loan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.loanapp.R
import com.example.loanapp.data.remote.model.LoanRequestBody
import com.example.loanapp.databinding.FragmentRequestLoanBinding
import com.example.loanapp.domain.entity.LoanCondition
import com.example.loanapp.presentation.loan.LoanConditionEvent
import com.example.loanapp.presentation.loan.LoanRequestEvent
import com.example.loanapp.presentation.loan.RequestLoanViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RequestLoanFragment : Fragment(R.layout.fragment_request_loan) {

    private var _binding: FragmentRequestLoanBinding? = null
    private val binding get() = _binding!!
    private val requestLoanViewModel by viewModels<RequestLoanViewModel>()
    private lateinit var loadedLoanCondition: LoanCondition


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

        requestLoanViewModel.getLoanCondition()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            requestLoanViewModel.loanFlow.collect { event ->
                when (event) {
                    is LoanRequestEvent.ShowProgressBar -> {
                        binding.progressBarRequestLoan.visibility = View.VISIBLE
                    }
                    is LoanRequestEvent.ShowSnackbar -> {
                        binding.progressBarRequestLoan.visibility = View.GONE
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                    }
                    is LoanRequestEvent.Success -> {
                        binding.progressBarRequestLoan.visibility = View.GONE
                        navigateToLoanRequestResultFragment()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {

            requestLoanViewModel.loanConditionFlow.collect { event ->
                when (event) {
                    is LoanConditionEvent.LoanConditionSuccess -> {
                        binding.apply {
                            event.loanCondition.also { loanCondition ->
                                loadedLoanCondition = loanCondition
                                txtViewLoanMaxAmount.text = getString(
                                    R.string.txt_view_loan_max_amount,
                                    loanCondition.maxAmount
                                )
                                txtViewLoanPercent.text = getString(
                                    R.string.txt_view_loan_percent,
                                    loanCondition.percent
                                )
                                txtViewLoanPeriod.text = getString(
                                    R.string.txt_view_loan_period,
                                    loanCondition.period
                                )
                            }
                        }
                    }
                    is LoanConditionEvent.ShowSnackBar -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }



        binding.btnRequestLoan.setOnClickListener {
            val amount = binding.editTxtAmount.text.toString().toInt()
            val firstName = binding.editTxtFirstName.text.toString()
            val lastName = binding.editTxtLastName.text.toString()
            val percent = binding.editTxtPercent.text.toString().toDouble()
            val period = binding.editTxtPeriod.text.toString().toInt()
            val phoneNumber = binding.editTextPhoneNumber.text.toString()

            val loan = LoanRequestBody(amount, firstName, lastName, percent, period, phoneNumber)
            if (checkLoanCondition(loan, loadedLoanCondition)) {
                binding.progressBarRequestLoan.visibility = View.VISIBLE
                requestLoanViewModel.requestLoan(loan)
            }
        }

    }

    private fun navigateToLoanRequestResultFragment() {
        val action = RequestLoanFragmentDirections.actionRequestLoanFragmentToLoanResultFragment()
        findNavController().navigate(action)
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