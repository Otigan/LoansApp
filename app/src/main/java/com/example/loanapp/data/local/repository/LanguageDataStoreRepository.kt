package com.example.loanapp.data.local.repository

import android.content.Context
import com.example.loanapp.data.local.data_source.LanguageDataSource
import javax.inject.Inject

class LanguageDataStoreRepository @Inject constructor(private val languageDataSource: LanguageDataSource) {


    suspend fun setLocale(context: Context, language: String) =
        languageDataSource.setLocale(context, language)

    suspend fun getLanguage() = languageDataSource.getLanguage()

    suspend fun onAttach(context: Context) =
        languageDataSource.onAttach(context)

}