package com.example.loanapp.ui.loan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.loanapp.R
import com.example.loanapp.databinding.FragmentLoansBinding
import com.example.loanapp.presentation.loan.LoanEvent
import com.example.loanapp.presentation.loan.LoansViewModel
import com.example.loanapp.ui.adapter.LoansAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoansFragment : Fragment(R.layout.fragment_loans) {

    private var _binding: FragmentLoansBinding? = null
    private val binding get() = _binding!!
    private lateinit var loansAdapter: LoansAdapter
    private val loansViewModel by viewModels<LoansViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoansBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loansAdapter = LoansAdapter()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            loansViewModel.apply {
                getAllLoans()
                loansEventFlow.collect { event ->
                    when (event) {
                        is LoanEvent.ShowSnackbar -> {
                            Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                        }
                        is LoanEvent.Success -> {
                            loansAdapter.loans = event.loans
                        }
                    }
                }
            }
        }

        binding.apply {
            recyclerViewLoans.apply {
                setHasFixedSize(true)
                adapter = loansAdapter
            }
            fabRequestLoan.setOnClickListener {
                navigateToRequestLoanFragment()
            }
        }
    }

    private fun navigateToRequestLoanFragment() {
        val action = LoansFragmentDirections.actionLoansFragmentToRequestLoanFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}