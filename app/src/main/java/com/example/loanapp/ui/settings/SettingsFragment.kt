package com.example.loanapp.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.loanapp.R
import com.example.loanapp.presentation.language.LanguageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment :
    PreferenceFragmentCompat() {

    private val languageViewModel by viewModels<LanguageViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_settings, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val languages = resources.getStringArray(R.array.language_code)
        val languagePreferenceName = resources.getString(R.string.settings_language_preference)
        val languagePreferences: ListPreference? = findPreference(languagePreferenceName)

        languagePreferences?.let {
            it.setOnPreferenceChangeListener { _, newValue ->
                languageViewModel.setLocale(requireActivity(), newValue.toString())
                activity?.recreate()
                true
            }
        }
        /*viewLifecycleOwner.lifecycleScope.launch {
            val lang = languageViewModel.getSelectedLanguage()
            languagePreferences?.setValueIndex(languages.indexOf(lang))
        }*/
    }
}