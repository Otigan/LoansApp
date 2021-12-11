package com.example.loanapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.loanapp.R
import com.example.loanapp.databinding.FragmentLogoutBinding
import com.example.loanapp.presentation.auth.LogoutViewModel
import com.example.loanapp.ui.loan.LoansFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutDialogFragment : DialogFragment(R.layout.fragment_logout) {

    private var _binding: FragmentLogoutBinding? = null
    private val binding get() = _binding!!
    private val logoutViewModel by viewModels<LogoutViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLogout.setOnClickListener {
                logoutViewModel.logout()
                navigateToLoginFragment()
                dismiss()
            }
            btnCancelLogout.setOnClickListener {
                dismiss()
            }
        }

    }

    private fun navigateToLoginFragment() {
        val action = LoansFragmentDirections.actionLoansFragmentToLoginFragment()
        findNavController().navigate(action)
    }
}