package com.example.loanapp.ui.settings

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.loanapp.R
import com.example.loanapp.presentation.language.LanguageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment() :
    PreferenceFragmentCompat() {

    private val languageViewModel by viewModels<LanguageViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_settings, rootKey)

        val languagePreferences: ListPreference? = findPreference("appLanguage")

        languagePreferences?.let {
            it.setOnPreferenceChangeListener { _, newValue ->
                languageViewModel.setLocale(requireActivity(), newValue.toString())
                activity?.recreate()
                true
            }
        }
    }
}