/*
package com.example.loanapp.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.emptyPreferences
import com.example.loanapp.data.local.LoginDataStorePref
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class LoginPreferencesRepository(
    private val loginPreferencesStore: DataStore<LoginPreferences>,
    context: Context
) {

    val preferencesFlow: Flow<String> = context.loginDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("LoginDataStore", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val name = preferences[LoginDataStorePref.PreferenceKey.name] ?: ""
            name
        }

    val userPreferencesFlow: Flow<LoginPreferences> = Context
        .map { preferences ->
            // Get our show completed value, defaulting to false if not set:
            val showCompleted = preferences[PreferencesKeys.SHOW_COMPLETED] ?: false
            UserPreferences(showCompleted)
        }

}


data class LoginPreferences(val login: String)*/
