package com.example.loanapp.data.local.repository

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKey {
    val language = stringPreferencesKey("language")
    val token = stringPreferencesKey("token")
}