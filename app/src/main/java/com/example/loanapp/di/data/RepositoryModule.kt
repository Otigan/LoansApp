package com.example.loanapp.di.data

import com.example.loanapp.data.remote.repository.loan.LoanRepositoryImpl
import com.example.loanapp.data.remote.repository.auth.LoginRepositoryImpl
import com.example.loanapp.data.remote.repository.auth.RegisterRepositoryImpl
import com.example.loanapp.domain.repository.loan.LoanRepository
import com.example.loanapp.domain.repository.auth.LoginRepository
import com.example.loanapp.domain.repository.auth.RegisterRepository
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