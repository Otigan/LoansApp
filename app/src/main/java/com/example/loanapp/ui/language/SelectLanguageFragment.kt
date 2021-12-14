package com.example.loanapp.ui.language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.loanapp.R
import com.example.loanapp.databinding.FragmentSelectLanguageBinding
import com.example.loanapp.domain.entity.Language
import com.example.loanapp.presentation.language.LanguageViewModel
import com.example.loanapp.ui.adapter.LanguageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectLanguageFragment : Fragment(R.layout.fragment_select_language) {

    private var _binding: FragmentSelectLanguageBinding? = null
    private val binding get() = _binding!!
    private lateinit var languageAdapter: LanguageAdapter
    private val languageViewModel by viewModels<LanguageViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectLanguageBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (languageViewModel.selectedLanguage.isNotBlank()) {
            navigateToLoginFragment()
        }

        val langs = listOf(Language("en"), Language("ru"))

        languageAdapter = LanguageAdapter(onClick = {
            languageViewModel.setLocale(requireActivity(), it.name)
            navigateToLoginFragment()
        })

        languageAdapter.languages = langs

        binding.apply {
            recyclerViewLanguage.apply {
                adapter = languageAdapter
                setHasFixedSize(true)
            }
        }
    }

    private fun navigateToLoginFragment() {
        val action = SelectLanguageFragmentDirections.actionSelectLanguageFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}