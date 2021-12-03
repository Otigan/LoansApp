package com.example.loanapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.loanapp.R
import com.example.loanapp.databinding.FragmentRegisterBinding
import com.example.loanapp.presentation.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

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

        registerViewModel.user.observe(viewLifecycleOwner, {
            Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
        })

        binding.apply {
            btnRegister.setOnClickListener {
                if (checkIfLoginEmpty() && checkIfPasswordEmpty() && checkIfPasswordsSame()) {
                    Toast.makeText(context, "Registered", Toast.LENGTH_SHORT).show()
                    val name = binding.registerLoginInput.text.toString()
                    val password = binding.registerPasswordInput.text.toString()
                    registerViewModel.register(name, password)

                }
            }
        }

    }


    private fun checkIfLoginEmpty(): Boolean {
        val loginInput = binding.registerLoginInput.text.toString()
        return if (loginInput.isEmpty()) {
            binding.txtFieldLoginRegister.error = "Пожалуйста, введите ваш логин"
            false
        } else {
            binding.txtFieldLoginRegister.error = ""
            true
        }
    }

    private fun checkIfPasswordEmpty(): Boolean {
        val passwordInput = binding.registerPasswordInput.text.toString()
        return if (passwordInput.isEmpty()) {
            binding.txtFieldPasswordRegister.error = "Пожалуйста, введите пароль"
            false
        } else {
            binding.txtFieldPasswordRegister.error = ""
            true
        }
    }

    private fun checkIfPasswordsSame(): Boolean {
        val passwordInput = binding.registerPasswordInput.text.toString()
        val passwordConf = binding.registerPasswordRepeatInput.text.toString()
        return when {
            passwordConf.isEmpty() -> {
                binding.txtFieldPasswordRegisterRepeat.error =
                    "Введите, пожалуйста, подтверждение пароля"
                false
            }
            passwordConf != passwordInput -> {
                binding.txtFieldPasswordRegisterRepeat.error = "Пароли не совпадают"
                false
            }
            else -> {
                binding.txtFieldPasswordRegisterRepeat.error = ""
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}