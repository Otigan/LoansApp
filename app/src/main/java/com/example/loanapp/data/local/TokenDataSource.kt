package com.example.loanapp.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.loanapp.data.local.repository.PreferenceKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenDataSource @Inject constructor(private val loginDataStore: DataStore<Preferences>) {

    suspend fun saveToken(token: String) {
        loginDataStore.edit { preferences ->
            preferences[PreferenceKey.token] = token
        }
    }

    suspend fun removeToken() = loginDataStore.edit {
        it.clear()
    }

    val savedToken: Flow<String> =
        loginDataStore.data.map { preferences ->
            preferences[PreferenceKey.token] ?: ""
        }


}