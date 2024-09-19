package com.hungln.retechtest.data.source.local

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hungln.retechtest.data.network.response.RepositoryItem
import com.hungln.retechtest.data.network.response.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users: List<User>)

    @Query("SELECT * FROM users WHERE login = :login LIMIT 1")
    suspend fun getUserByLogin(login: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: User): Int

    @Transaction
    suspend fun replaceUser(newUser: User): Boolean {
        val existingUser = getUserByLogin(newUser.login ?: "")
        return if (existingUser != null) {
            newUser.uid = existingUser.uid
            val updatedRows = updateUser(newUser)
            updatedRows > 0
        } else {
            val insertedId = insertUser(newUser)
            insertedId != -1L
        }
    }
}