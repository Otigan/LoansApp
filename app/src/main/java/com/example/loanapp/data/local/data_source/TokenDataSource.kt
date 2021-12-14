package com.example.loanapp.data.local.data_source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.loanapp.data.local.util.PreferenceKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenDataSource @Inject constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKey.token] = token
        }
    }

    suspend fun removeToken() = dataStore.edit {
        it.clear()
    }

    val savedToken: Flow<String> =
        dataStore.data.map { preferences ->
            preferences[PreferenceKey.token] ?: ""
        }


}