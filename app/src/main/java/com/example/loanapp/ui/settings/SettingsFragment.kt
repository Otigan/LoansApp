package com.example.loanapp.ui.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.loanapp.R
import com.example.loanapp.util.Localization
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment() :
    PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_settings, rootKey)

        val array = resources.getStringArray(R.array.language_code)

        val languagePreferences: ListPreference? = findPreference("appLanguage")

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        languagePreferences?.let {
            it.setOnPreferenceChangeListener { _, newValue ->
                Log.d("SettingsFragment", "onCreatePreferences: $newValue")
                with(sharedPref.edit()) {
                    putString("lang", newValue.toString())
                    apply()
                }
                Localization().setLocale(requireActivity(), newValue.toString())
                requireActivity().recreate()
                true
            }
        }
    }
}