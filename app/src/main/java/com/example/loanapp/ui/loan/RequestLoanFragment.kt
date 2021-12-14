package com.example.loanapp.ui.loan

import android.os.Bundle
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
import com.example.loanapp.presentation.loan.RequestLoanViewModel
import com.example.loanapp.util.Extensions.checkIfNotEmpty
import com.example.loanapp.util.Extensions.displayProgressBar
import com.example.loanapp.util.Extensions.displaySnackbar
import com.example.loanapp.util.LoanConditionEvent
import com.example.loanapp.util.LoanRequestEvent
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
                    is LoanRequestEvent.Loading -> {
                        binding.progressBarRequestLoan.visibility = View.VISIBLE
                    }
                    is LoanRequestEvent.Error -> {
                        binding.progressBarRequestLoan.visibility = View.GONE
                        binding.root.displaySnackbar(event.message)
                    }
                    is LoanRequestEvent.Success -> {
                        binding.progressBarRequestLoan.visibility = View.GONE
                        navigateToLoanRequestResultFragment(event.loan.amount)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {

            requestLoanViewModel.loanConditionFlow.collect { event ->
                when (event) {
                    is LoanConditionEvent.Success -> {
                        binding.apply {
                            progressBarRequestLoan.displayProgressBar(false)
                            event.loanCondition.apply {
                                loadedLoanCondition = this
                                txtViewLoanMaxAmount.text = getString(
                                    R.string.txt_view_loan_max_amount,
                                    maxAmount
                                )
                                txtViewLoanPercent.text = getString(
                                    R.string.txt_view_loan_percent,
                                    percent
                                )
                                txtViewLoanPeriod.text = getString(
                                    R.string.txt_view_loan_period,
                                    period
                                )
                            }
                        }
                    }
                    is LoanConditionEvent.Error -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                        binding.progressBarRequestLoan.displayProgressBar(false)
                    }
                    LoanConditionEvent.Loading -> {
                        binding.progressBarRequestLoan.displayProgressBar(true)
                    }
                }
            }
        }

        binding.btnRequestLoan.setOnClickListener {

            if (checkInput()) {
                val amount = binding.txtFieldAmount.editText?.text.toString().toInt()
                val firstName = binding.txtFieldFirstName.editText?.text.toString()
                val lastName = binding.txtFieldLastName.editText?.text.toString()
                val phoneNumber = binding.txtFieldPhoneNumber.editText?.text.toString()
                val loan =
                    LoanRequestBody(
                        amount,
                        firstName,
                        lastName,
                        loadedLoanCondition.percent,
                        loadedLoanCondition.period,
                        phoneNumber
                    )
                if (checkMaxAmount(loan)) {
                    binding.progressBarRequestLoan.visibility = View.VISIBLE
                    requestLoanViewModel.requestLoan(loan)
                }
            }

        }
    }

    private fun navigateToLoanRequestResultFragment(loan_amount: Int) {
        val action =
            RequestLoanFragmentDirections.actionRequestLoanFragmentToLoanResultFragment(loan_amount)
        findNavController().navigate(action)
    }

    private fun checkInput(): Boolean {
        val amount = binding.txtFieldAmount.checkIfNotEmpty()
        val firstName = binding.txtFieldFirstName.checkIfNotEmpty()
        val lastName = binding.txtFieldLastName.checkIfNotEmpty()
        val phoneNumber = binding.txtFieldPhoneNumber.checkIfNotEmpty()

        return amount && firstName && lastName && phoneNumber
    }

    private fun checkMaxAmount(loan: LoanRequestBody): Boolean {
        return when {
            loan.amount > loadedLoanCondition.maxAmount -> {
                binding.txtFieldAmount.error = getString(R.string.request_loan_amount_error)
                false
            }
            else -> {
                binding.txtFieldAmount.error = ""
                true
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}