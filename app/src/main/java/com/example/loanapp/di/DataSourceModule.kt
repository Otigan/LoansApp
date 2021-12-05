package com.example.loanapp.di

import com.example.loanapp.data.remote.data_source.auth.LoginDataSource
import com.example.loanapp.data.remote.data_source.auth.LoginDataSourceImpl
import com.example.loanapp.data.remote.data_source.auth.RegisterDataSource
import com.example.loanapp.data.remote.data_source.auth.RegisterDataSourceImpl
import com.example.loanapp.data.remote.data_source.loan.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLoginDataSource(impl: LoginDataSourceImpl): LoginDataSource

    @Binds
    @Singleton
    abstract fun bindRegisterDataSource(impl: RegisterDataSourceImpl): RegisterDataSource

    @Binds
    @Singleton
    abstract fun bindLoanDataSource(impl: LoanDataSourceImpl): LoanDataSource

    @Binds
    @Singleton
    abstract fun bindLoanConditionDataSource(impl: LoanConditionDataSourceImpl): LoanConditionDataSource

    @Binds
    @Singleton
    abstract fun bindLoanRequestDataSource(impl: LoanRequestDataSourceImpl): LoanRequestDataSource
}