package com.example.loanapp.data.local.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.loginDataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

@Singleton
class LoginDataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun saveToken(token: String) {
        context.loginDataStore.edit { preferences ->
            preferences[PreferenceKey.token] = token
        }
    }

    val savedToken: Flow<String> =
        context.loginDataStore.data.map { preferences ->
            preferences[PreferenceKey.token] ?: ""
        }
}