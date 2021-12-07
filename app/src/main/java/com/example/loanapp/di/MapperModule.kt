package com.example.loanapp.di

import com.example.loanapp.data.remote.mapper.LoanDtoMapper
import com.example.loanapp.data.remote.mapper.UserDtoMapper
import com.example.loanapp.data.remote.model.LoanDto
import com.example.loanapp.data.remote.model.UserDto
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.entity.User
import com.example.loanapp.domain.util.Mapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Binds
    @Singleton
    @LoanMapper
    abstract fun bindLoanDtoMapper(impl: LoanDtoMapper): Mapper<LoanDto, Loan>

    @Binds
    @Singleton
    @UserMapper
    abstract fun bindUserDtoMapper(impl: UserDtoMapper): Mapper<UserDto, User>

}