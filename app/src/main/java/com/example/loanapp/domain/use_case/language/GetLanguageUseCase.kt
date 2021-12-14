package com.example.loanapp.domain.use_case.language

import com.example.loanapp.data.local.repository.LanguageDataStoreRepository
import javax.inject.Inject

class GetLanguageUseCase @Inject constructor(private val languageDataStoreRepository: LanguageDataStoreRepository) {

    operator fun invoke() = languageDataStoreRepository.getLanguage()

}