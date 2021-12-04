package com.example.loanapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.loanapp.R
import com.example.loanapp.databinding.FragmentLoginBinding
import com.example.loanapp.presentation.LoginViewModel
import com.example.loanapp.util.Status
import dagger.hilt.android.AndroidEntryPoint

const val TOKEN = "BEARER_TOKEN"

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

        login()

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

    private fun login() {
        loginViewModel.loginResult.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let { resource ->
                when (resource.status) {

                    Status.LOADING -> {
                        Toast.makeText(context, "LOGGING IN", Toast.LENGTH_SHORT).show()
                    }

                    Status.SUCCESS -> {
                        resource.data?.let {
                            navigateToLoansFragment()
                        }
                    }

                    Status.ERROR -> {

                    }
                }
            }
        })
    }

    /* private fun login(login: String, password: String) {
         loginViewModel.login(login, password)
         loginViewModel.token.observe(viewLifecycleOwner, { response ->
             if (response.isSuccessful) {
                 val pref = activity?.getPreferences(Context.MODE_PRIVATE)
                 pref?.let {
                     with(it.edit()) {
                         putString(TOKEN, response.body())
                         apply()
                     }
                 }
                 findNavController().navigate(R.id.loansFragment)
             } else {
                 if (response.code() == 404) {
                     Toast.makeText(
                         context,
                         "Данный аккаунт не найден, проверьте правильность ввода данных",
                         Toast.LENGTH_SHORT
                     ).show()
                 }
             }
         })
     }*/


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