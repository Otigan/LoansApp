package com.example.loanapp.domain.use_case.language

import android.content.Context
import com.example.loanapp.data.local.repository.LanguageDataStoreRepository
import javax.inject.Inject

class AttachUseCase @Inject constructor(private val languageDataStoreRepository: LanguageDataStoreRepository) {

    suspend operator fun invoke(context: Context) =
        languageDataStoreRepository.onAttach(context)
}