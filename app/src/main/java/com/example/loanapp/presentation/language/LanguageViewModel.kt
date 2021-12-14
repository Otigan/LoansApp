package com.example.loanapp.presentation.language

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapp.domain.use_case.language.GetLanguageUseCase
import com.example.loanapp.domain.use_case.language.SelectLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val selectLanguageUseCase: SelectLanguageUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
) : ViewModel() {

    suspend fun getSelectedLanguage() = withContext(Dispatchers.IO) {
        getLanguageUseCase()
    }

    fun setLocale(context: Context, lang: String) = viewModelScope.launch(Dispatchers.IO) {
        selectLanguageUseCase(context, lang)
    }

}