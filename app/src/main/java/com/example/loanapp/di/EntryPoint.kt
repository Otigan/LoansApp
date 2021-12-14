package com.example.loanapp.di

import com.example.loanapp.presentation.language.LocalizationHelper
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WrapperEntryPoint {
    val localeHelper: LocalizationHelper
}