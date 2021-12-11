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
import com.example.loanapp.presentation.loan.LoanConditionEvent
import com.example.loanapp.presentation.loan.LoanRequestEvent
import com.example.loanapp.presentation.loan.RequestLoanViewModel
import com.example.loanapp.util.Extensions.checkIfNotEmpty
import com.example.loanapp.util.Extensions.displaySnackbar
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

            if (checkInput()) {
                val amount = binding.txtFieldAmount.editText?.text.toString().toInt()
                val firstName = binding.txtFieldFirstName.editText?.text.toString()
                val lastName = binding.txtFieldLastName.editText?.text.toString()
                val percent = binding.txtFieldPercent.editText?.text.toString().toDouble()
                val period = binding.txtFieldPeriod.editText?.text.toString().toInt()
                val phoneNumber = binding.txtFieldPhoneNumber.editText?.text.toString()
                val loan =
                    LoanRequestBody(amount, firstName, lastName, percent, period, phoneNumber)
                if (checkLoanCondition(loan)) {
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
        val percent = binding.txtFieldPercent.checkIfNotEmpty()
        val period = binding.txtFieldPeriod.checkIfNotEmpty()
        val phoneNumber = binding.txtFieldPhoneNumber.checkIfNotEmpty()

        return amount && firstName && lastName && percent && period && phoneNumber
    }

    private fun checkLoanCondition(loan: LoanRequestBody): Boolean {
        return when {
            loan.amount > loadedLoanCondition.maxAmount -> {
                binding.txtFieldAmount.error = getString(R.string.request_loan_amount_error)
                false
            }
            loan.percent != loadedLoanCondition.percent -> {
                binding.txtFieldPercent.error = getString(R.string.request_loan_percent_error)
                false
            }
            loan.period != loadedLoanCondition.period -> {
                binding.txtFieldPeriod.error = getString(R.string.request_loan_period_error)
                false
            }
            else -> {
                binding.txtFieldAmount.error = ""
                binding.txtFieldPercent.error = ""
                binding.txtFieldPeriod.error = ""
                true
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}