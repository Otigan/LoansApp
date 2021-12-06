package com.example.loanapp.ui.auth

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
import com.example.loanapp.databinding.FragmentLoginBinding
import com.example.loanapp.presentation.auth.AuthEvent
import com.example.loanapp.presentation.auth.LoginViewModel
import com.google.android.material.snackbar.Snackbar
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

        loginViewModel.skipLogin()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            loginViewModel.loginEventFlow.collect { event ->
                when (event) {
                    is AuthEvent.Success -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                        navigateToLoansFragment()
                    }
                    is AuthEvent.ShowSnackbar -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.apply {
            txtGoToRegister.setOnClickListener {
                navigateToRegisterFragment()
            }
            btnLogin.setOnClickListener {
                if (checkIfLoginEmpty() && checkIfPasswordEmpty()) {
                    val name = binding.loginInput.text.toString()
                    val password = binding.passwordInput.text.toString()
                    loginViewModel.login(name, password)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkIfLoginEmpty(): Boolean {
        val loginInput = binding.loginInput.text.toString()
        return if (loginInput.isEmpty()) {
            binding.txtFieldLogin.error = "Пожалуйста, введите ваш логин"
            false
        } else {
            binding.txtFieldLogin.error = ""
            true
        }
    }

    private fun checkIfPasswordEmpty(): Boolean {
        val passwordInput = binding.passwordInput.text.toString()
        return if (passwordInput.isEmpty()) {
            binding.txtFieldPassword.error = "Пожалуйста, введите пароль"
            false
        } else {
            binding.txtFieldPassword.error = ""
            true
        }
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