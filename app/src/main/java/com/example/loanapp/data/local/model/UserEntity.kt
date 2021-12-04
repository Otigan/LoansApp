package com.example.loanapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val user_id: Int = 0,
    val name: String? = null,
    val role: String? = null,
    val token: String? = null
)
