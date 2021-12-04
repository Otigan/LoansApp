package com.example.loanapp.di

import com.example.loanapp.data.remote.data_source.LoginDataSource
import com.example.loanapp.data.remote.data_source.LoginDataSourceImpl
import com.example.loanapp.data.remote.data_source.RegisterDataSource
import com.example.loanapp.data.remote.data_source.RegisterDataSourceImpl
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

}