package com.example.loanapp.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.loanapp.data.local.model.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Query("UPDATE users SET token = null WHERE user_id = :user_id")
    fun removeToken(user_id: Int)

    @Query("UPDATE users SET token = :token WHERE name = :name")
    suspend fun insertToken(token: String, name: String)

    @Query("SELECT * FROM users WHERE name = :name")
    suspend fun searchByUserName(name: String): UserEntity

}