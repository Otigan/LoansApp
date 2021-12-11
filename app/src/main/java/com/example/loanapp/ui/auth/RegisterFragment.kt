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
import com.example.loanapp.databinding.FragmentRegisterBinding
import com.example.loanapp.presentation.auth.RegisterViewModel
import com.example.loanapp.util.AuthEvent
import com.example.loanapp.util.Extensions.checkIfNotEmpty
import com.example.loanapp.util.Extensions.checkIfPasswordsAreSame
import com.example.loanapp.util.Extensions.displaySnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            registerViewModel.userFlow.collect { event ->
                when (event) {
                    is AuthEvent.Error -> {
                        binding.root.displaySnackbar(event.message)
                    }
                    is AuthEvent.Success -> {
                        navigateToLoginFragment()
                    }
                    else -> {}
                }
            }
        }

        binding.apply {
            btnRegister.setOnClickListener {
                if (checkTextFields()) {
                    Toast.makeText(context, "Registered", Toast.LENGTH_SHORT).show()
                    val name = binding.txtFieldLoginRegister.editText?.text.toString()
                    val password = binding.txtFieldPasswordRegister.editText?.text.toString()
                    registerViewModel.register(name, password)

                }
            }
        }
    }

    private fun checkTextFields(): Boolean {
        val loginTextField = binding.txtFieldLoginRegister.checkIfNotEmpty()
        val passwordTextField = binding.txtFieldPasswordRegister.checkIfNotEmpty()
        val confPasswordTextField = binding.txtFieldPasswordRegisterRepeat.checkIfNotEmpty()
        val passwordsAreSame = binding.txtFieldPasswordRegisterRepeat.checkIfPasswordsAreSame(
            binding.txtFieldPasswordRegister
        )
        return loginTextField && passwordTextField && confPasswordTextField && passwordsAreSame
    }

    private fun navigateToLoginFragment() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}