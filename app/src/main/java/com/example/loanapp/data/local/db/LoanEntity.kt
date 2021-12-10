package com.example.loanapp.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loans")
data class LoanEntity(
    @PrimaryKey val id: Int,
    val amount: Int,
    val date: String,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val state: String,
)