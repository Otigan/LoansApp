package com.example.loanapp.di

import android.content.Context
import androidx.room.Room
import com.example.loanapp.data.local.db.LoansDatabase
import com.example.loanapp.data.local.db.LoansDatabase.Companion.DATABASE_NAME
import com.example.loanapp.data.local.db.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideLoansDatabase(@ApplicationContext context: Context): LoansDatabase =
        Room.databaseBuilder(
            context,
            LoansDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideUserDao(db: LoansDatabase): UserDao = db.getUserDao()

}