package com.example.loanapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LoanEntity::class], version = 1)
abstract class LoansDatabase : RoomDatabase() {

    abstract fun getLoansDao(): LoansDao
}