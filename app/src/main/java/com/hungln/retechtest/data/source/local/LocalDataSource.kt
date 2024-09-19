package com.hungln.retechtest.data.source.local

import com.hungln.retechtest.data.network.response.RepositoryItem
import com.hungln.retechtest.data.network.response.RepositoryWithOwner
import com.hungln.retechtest.data.network.response.User
import kotlinx.coroutines.flow.Flow
import kotlin.math.log

class LocalDataSource(
    private val userDao: UserDao,
    private val userRepositoryDao: UserRepositoryDao
) {
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    suspend fun insertAllUsers(users: List<User>) = userDao.insertAllUsers(users)

    suspend fun getUserByLogin(login: String): User? = userDao.getUserByLogin(login)

    suspend fun insertUser(newUser: User) = userDao.insertUser(newUser)

    suspend fun updateUser(newUser: User) = userDao.updateUser(newUser)

    suspend fun replaceUser(newUser: User) = userDao.replaceUser(newUser)

    suspend fun insertRepositories(repositories: List<RepositoryItem>): Boolean {
        val result = userRepositoryDao.insertRepositories(repositories)
        return result.none { it == -1L }  // Return false if any insert failed
    }

    fun getRepositoriesWithOwner(login: String): Flow<List<RepositoryWithOwner>> =
        userRepositoryDao.getRepositoriesWithOwner(login)

}