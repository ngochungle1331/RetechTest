package com.hungln.retechtest.data.source

import com.hungln.retechtest.data.network.response.OrganizationItem
import com.hungln.retechtest.data.network.response.RepositoryItem
import com.hungln.retechtest.data.network.response.RepositoryWithOwner
import com.hungln.retechtest.data.network.response.User
import com.hungln.retechtest.data.source.interf.AppDataSource
import com.hungln.retechtest.data.source.interf.UserLocalDataSource
import com.hungln.retechtest.data.source.local.LocalDataSource
import com.hungln.retechtest.data.source.local.UserDao
import com.hungln.retechtest.data.source.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : AppDataSource, UserLocalDataSource {

    val remote = remoteDataSource
    val local = localDataSource

    override suspend fun getListUsers(): List<User> {
        val users = localDataSource.getAllUsers().first()
        return users.ifEmpty {
            remote.getListUsers()
        }
    }

    override suspend fun getUserDetails(loginUser: String): User =
        remote.getUserDetails(loginUser = loginUser)

    override suspend fun getListRepos(reposUrl: String): List<RepositoryItem> =
        remote.getListRepos(reposUrl = reposUrl)

    override suspend fun getListOrganizations(organizationUrl: String): List<OrganizationItem> =
        remote.getListOrganizations(organizationUrl = organizationUrl)

    override fun getAllUsers(): Flow<List<User>> =
        local.getAllUsers()

    override suspend fun insertAllUsers(users: List<User>) =
        local.insertAllUsers(users)

    override suspend fun getUserByLogin(login: String): User? =
        local.getUserByLogin(login)

    override suspend fun insertUser(newUser: User) =
        local.insertUser(newUser)

    override suspend fun updateUser(newUser: User) =
        local.updateUser(newUser)

    override suspend fun replaceUser(newUser: User) =
        local.replaceUser(newUser)

    override suspend fun insertRepositories(repositories: List<RepositoryItem>): Boolean {
        return local.insertRepositories(repositories)
    }

    override suspend fun getRepositoriesWithOwner(login: String): Flow<List<RepositoryWithOwner>> =
        local.getRepositoriesWithOwner(login)

}