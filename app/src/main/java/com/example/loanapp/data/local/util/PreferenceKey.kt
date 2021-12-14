package com.example.loanapp.data.local.util

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKey {
    val language = stringPreferencesKey("language")
    val token = stringPreferencesKey("token")
}