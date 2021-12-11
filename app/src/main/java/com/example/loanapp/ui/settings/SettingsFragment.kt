package com.example.loanapp.ui.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.loanapp.R
import com.example.loanapp.presentation.auth.LoginViewModel
import com.example.loanapp.util.Common.SELECTED_LANGUAGE
import com.example.loanapp.util.Localization
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment() :
    PreferenceFragmentCompat() {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_settings, rootKey)

        val languagePreferences: ListPreference? = findPreference("appLanguage")

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        languagePreferences?.let {
            it.setOnPreferenceChangeListener { _, newValue ->
                with(sharedPref.edit()) {
                    putString(SELECTED_LANGUAGE, newValue.toString())
                    apply()
                }
                Localization().setLocale(requireActivity(), newValue.toString())
                requireActivity().recreate()
                true
            }
        }
    }
}