package com.hungln.retechtest.data.source.interf

import com.hungln.retechtest.data.network.response.OrganizationItem
import com.hungln.retechtest.data.network.response.RepositoryItem
import com.hungln.retechtest.data.network.response.User

interface AppDataSource {
    suspend fun getListUsers(): List<User>

    suspend fun getUserDetails(loginUser: String): User

    suspend fun getListRepos(reposUrl: String): List<RepositoryItem>

    suspend fun getListOrganizations(organizationUrl: String): List<OrganizationItem>
}