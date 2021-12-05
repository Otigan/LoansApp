package com.example.loanapp.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginDataStorePref @Inject constructor(@ApplicationContext private val context: Context) {

    object PreferenceKey {
        val name = stringPreferencesKey("login")
    }

    private val Context.loginDataStore: DataStore<Preferences> by preferencesDataStore(name = "login")

    val loginDataStore: DataStore<Preferences> = context.loginDataStore

    suspend fun saveLoginToDataStore(name: String) {
        loginDataStore.edit { preference ->
            preference[PreferenceKey.name] = name
        }
    }

    val preferencesFlow: Flow<String> = loginDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("LoginDataStore", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val name = preferences[PreferenceKey.name] ?: ""
            name
        }
}