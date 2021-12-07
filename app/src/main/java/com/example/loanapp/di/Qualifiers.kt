package com.example.loanapp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoanMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserMapper