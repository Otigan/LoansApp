package com.example.loanapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.loanapp.R
import com.example.loanapp.databinding.FragmentLoginBinding
import com.example.loanapp.presentation.auth.LoginViewModel
import com.example.loanapp.util.Extensions.checkIfNotEmpty
import com.example.loanapp.util.Extensions.displaySnackbar
import com.example.loanapp.util.LoginEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.checkToken()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            loginViewModel.loginEventFlow.collect { event ->
                when (event) {
                    is LoginEvent.Success -> {
                        binding.progressBarLogin.visibility = View.GONE
                        navigateToLoansFragment()
                    }
                    is LoginEvent.Error -> {
                        binding.progressBarLogin.visibility = View.GONE
                        binding.root.displaySnackbar(event.message)
                    }
                    is LoginEvent.Loading -> {
                        binding.progressBarLogin.visibility = View.VISIBLE
                    }
                    is LoginEvent.Logout -> {

                    }
                }
            }
        }

        binding.apply {
            txtGoToRegister.setOnClickListener {
                navigateToRegisterFragment()
            }
            btnLogin.setOnClickListener {
                if (checkTextFields()) {
                    val name = binding.txtFieldLogin.editText?.text.toString()
                    val password = binding.txtFieldPassword.editText?.text.toString()
                    loginViewModel.login(name, password)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkTextFields(): Boolean {
        val loginInputLayout = binding.txtFieldLogin.checkIfNotEmpty()
        val passwordInputLayout = binding.txtFieldPassword.checkIfNotEmpty()
        return loginInputLayout && passwordInputLayout
    }

    private fun navigateToLoansFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToLoansFragment()
        findNavController().navigate(action)
    }

    private fun navigateToRegisterFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }
}