package com.example.loanapp.ui.loan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.loanapp.R
import com.example.loanapp.databinding.FragmentLoanResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoanResultFragment : Fragment(R.layout.fragment_loan_result) {

    private var _binding: FragmentLoanResultBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoanResultBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGoBackToLoans.setOnClickListener {
            navigateToLoansFragment()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToLoansFragment() {
        val action = LoanResultFragmentDirections.actionLoanResultFragmentToLoansFragment()
        findNavController().navigate(action)
    }

}