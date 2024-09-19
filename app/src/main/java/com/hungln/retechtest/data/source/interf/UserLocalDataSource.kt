package com.hungln.retechtest.data.source.interf

import com.hungln.retechtest.data.network.response.RepositoryItem
import com.hungln.retechtest.data.network.response.RepositoryWithOwner
import com.hungln.retechtest.data.network.response.User
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    fun getAllUsers(): Flow<List<User>>

    suspend fun insertAllUsers(users: List<User>)

    suspend fun getUserByLogin(login: String): User?

    suspend fun insertUser(newUser: User): Long

    suspend fun updateUser(newUser: User): Int

    suspend fun replaceUser(newUser: User): Boolean

    suspend fun insertRepositories(repositories: List<RepositoryItem>): Boolean

    suspend fun getRepositoriesWithOwner(login: String): Flow<List<RepositoryWithOwner>>
}