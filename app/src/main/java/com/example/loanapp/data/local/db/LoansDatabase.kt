package com.example.loanapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.loanapp.data.local.model.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class LoansDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        const val DATABASE_NAME = "loans_db"
    }
}