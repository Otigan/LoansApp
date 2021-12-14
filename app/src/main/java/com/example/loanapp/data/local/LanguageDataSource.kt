package com.example.loanapp.data.local

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.loanapp.data.local.repository.PreferenceKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

class LanguageDataSource @Inject constructor(
    private val loginDataStore: DataStore<Preferences>,
) {

    fun setLocale(context: Context, language: String): Context {
        runBlocking {
            saveLanguage(language)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language)
        }

        return updateResourcesLegacy(context, language)
    }

    fun getLanguage(): String {
        val lang: String
        runBlocking {
            lang = selectedLanguage.first()
        }
        return lang
    }

    private suspend fun saveLanguage(language: String) {
        loginDataStore.edit { preferences ->
            preferences[PreferenceKey.language] = language
        }
    }

    fun onAttach(context: Context): Context {
        val lang: String
        runBlocking {
            lang = selectedLanguage.first()
        }
        return setLocale(context, lang)
    }

    private val selectedLanguage: Flow<String> =
        loginDataStore.data.map { preferences ->
            preferences[PreferenceKey.language] ?: ""
        }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }

    @Suppress("DEPRECATION")
    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources

        val configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)

        resources.updateConfiguration(configuration, resources.displayMetrics)

        return context
    }

}