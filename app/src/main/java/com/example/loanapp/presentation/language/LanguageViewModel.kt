package com.example.loanapp.presentation.language

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val localeHelper: LocalizationHelper
) :
    ViewModel() {

    fun onAttach(context: Context) = localeHelper.onAttach(context)

    val selectedLanguage = localeHelper.getLanguage()

    fun setLocale(context: Context, lang: String) = localeHelper.setLocale(context, lang)

}