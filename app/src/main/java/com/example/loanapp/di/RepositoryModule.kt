package com.example.loanapp.di

import com.example.loanapp.data.repository.LoanRepositoryImpl
import com.example.loanapp.data.repository.LoginRepositoryImpl
import com.example.loanapp.data.repository.RegisterRepositoryImpl
import com.example.loanapp.domain.repository.LoanRepository
import com.example.loanapp.domain.repository.LoginRepository
import com.example.loanapp.domain.repository.RegisterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(impl: RegisterRepositoryImpl): RegisterRepository

    @Binds
    @Singleton
    abstract fun bindLoanRepository(impl: LoanRepositoryImpl): LoanRepository

}