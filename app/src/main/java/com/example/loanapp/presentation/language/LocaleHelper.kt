package com.example.loanapp.presentation.language

import android.content.Context
import com.example.loanapp.domain.use_case.language.AttachUseCase
import com.example.loanapp.domain.use_case.language.GetLanguageUseCase
import com.example.loanapp.domain.use_case.language.SelectLanguageUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalizationHelper @Inject constructor(
    private val attachUseCase: AttachUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val selectLanguageUseCase: SelectLanguageUseCase
) {

    fun onAttach(context: Context): Context = attachUseCase(context)

    fun getLanguage(): String = getLanguageUseCase()

    fun setLocale(context: Context, language: String): Context =
        selectLanguageUseCase(context, language)

}