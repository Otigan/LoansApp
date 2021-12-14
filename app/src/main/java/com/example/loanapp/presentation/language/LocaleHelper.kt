package com.example.loanapp.presentation.language

import android.content.Context
import com.example.loanapp.domain.use_case.language.AttachUseCase
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalizationHelper @Inject constructor(
    private val attachUseCase: AttachUseCase,
) {

    fun onAttach(context: Context): Context = runBlocking {
        attachUseCase(context)
    }
}