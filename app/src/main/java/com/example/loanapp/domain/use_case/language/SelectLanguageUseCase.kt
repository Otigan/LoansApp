package com.example.loanapp.domain.use_case.language

import android.content.Context
import com.example.loanapp.data.local.repository.LanguageDataStoreRepository
import javax.inject.Inject

class SelectLanguageUseCase @Inject constructor(private val languageDataStoreRepository: LanguageDataStoreRepository) {

    operator fun invoke(context: Context, language: String) =
        languageDataStoreRepository.setLocale(context, language)

}