package com.example.loanapp.ui.loans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.loanapp.R
import com.example.loanapp.data.local.LoginDataStorePref
import com.example.loanapp.databinding.FragmentLoansBinding
import com.example.loanapp.presentation.loan.LoansViewModel
import com.example.loanapp.ui.adapter.LoansAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@AndroidEntryPoint
class LoansFragment : Fragment(R.layout.fragment_loans) {

    private var _binding: FragmentLoansBinding? = null
    private val binding get() = _binding!!
    private lateinit var loansAdapter: LoansAdapter
    private val loansViewModel by viewModels<LoansViewModel>()

    @Inject
    lateinit var loginDataStorePref: LoginDataStorePref

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


        val name: Flow<String> = loginDataStorePref.loginDataStore.data.map { preferences ->
            preferences[LoginDataStorePref.PreferenceKey.name] ?: ""
        }

        loansViewModel.getAllLoans(name)

        binding.apply {
            recyclerViewLoans.apply {
                setHasFixedSize(true)
                adapter = loansAdapter
            }
            fabRequestLoan.setOnClickListener {
                navigateToRequestLoanFragment()
            }
        }

        loansViewModel.loans.observe(viewLifecycleOwner, {
            loansAdapter.loans = it
            Toast.makeText(context, it[0].id.toString(), Toast.LENGTH_SHORT).show()
        })
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