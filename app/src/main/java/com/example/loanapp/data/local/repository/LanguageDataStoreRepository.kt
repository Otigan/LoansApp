package com.example.loanapp.data.local.repository

import android.content.Context
import com.example.loanapp.data.local.LanguageDataSource
import javax.inject.Inject

class LanguageDataStoreRepository @Inject constructor(private val languageDataSource: LanguageDataSource) {


    fun setLocale(context: Context, language: String) =
        languageDataSource.setLocale(context, language)

    fun getLanguage() = languageDataSource.getLanguage()

    fun onAttach(context: Context) = languageDataSource.onAttach(context)

}