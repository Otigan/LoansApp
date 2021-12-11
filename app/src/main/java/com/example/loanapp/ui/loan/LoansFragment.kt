package com.example.loanapp.ui.loan

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.loanapp.R
import com.example.loanapp.databinding.FragmentLoansBinding
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.presentation.auth.LogoutViewModel
import com.example.loanapp.presentation.loan.LoanEvent
import com.example.loanapp.presentation.loan.LoansViewModel
import com.example.loanapp.ui.adapter.LoansAdapter
import com.example.loanapp.util.Common.sortLoansByDescending
import com.example.loanapp.util.Extensions.displaySnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class LoansFragment : Fragment(R.layout.fragment_loans) {

    private var _binding: FragmentLoansBinding? = null
    private val binding get() = _binding!!
    private lateinit var loansAdapter: LoansAdapter
    private val loansViewModel by viewModels<LoansViewModel>()
    private val logoutViewModel by viewModels<LogoutViewModel>()

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

        loansAdapter = LoansAdapter(onClick = { loan ->
            navigateToLoanDetailFragment(loan)
        })

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            loansViewModel.apply {
                getAllLoans()
                loansEventFlow.collect { event ->
                    when (event) {
                        is LoanEvent.ShowSnackbar -> {
                            binding.progressBarLoans.visibility = View.GONE
                            binding.root.displaySnackbar(event.message)
                            val loans = event.data
                            loansAdapter.submitList(sortLoansByDescending(loans))
                        }
                        is LoanEvent.Success -> {
                            binding.progressBarLoans.visibility = View.GONE
                            val loans = event.loans
                            loansAdapter.submitList(sortLoansByDescending(loans))
                        }
                        is LoanEvent.ShowProgressBar -> {
                            binding.progressBarLoans.visibility = View.VISIBLE
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
        setHasOptionsMenu(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_settings -> {
                navigateToSettingsFragment()
            }
            R.id.item_logout -> {
                AlertDialog.Builder(requireActivity())
                    .setTitle(getString(R.string.dialog_logout_title))
                    .setNegativeButton(getString(R.string.dialog_logout_negative_button)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(
                        getString(R.string.dialog_logout_positive_button)
                    ) { _, _ -> logout() }
                    .create()
                    .show()

            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.menu_loans_fragment, menu)
    }

    private fun logout() {
        logoutViewModel.logout()
        val action = LoansFragmentDirections.actionLoansFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    private fun navigateToSettingsFragment() {
        val action = LoansFragmentDirections.actionLoansFragmentToSettingsFragment()
        findNavController().navigate(action)
    }

    private fun navigateToLoanDetailFragment(loan: Loan) {
        val action = LoansFragmentDirections.actionLoansFragmentToLoanDetailFragment(loan)
        findNavController().navigate(action)
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