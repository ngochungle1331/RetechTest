package com.hungln.retechtest.data.source.remote

import com.hungln.retechtest.data.network.ApiService
import com.hungln.retechtest.data.network.response.OrganizationItem
import com.hungln.retechtest.data.network.response.RepositoryItem
import com.hungln.retechtest.data.network.response.User
import com.hungln.retechtest.data.source.interf.AppDataSource
import com.hungln.retechtest.util.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource(private val apiService: ApiService) : AppDataSource {

    override suspend fun getListUsers(): List<User> = apiService.getUsers()

    override suspend fun getUserDetails(loginUser: String): User =
        apiService.getUserDetails(loginUser)

    override suspend fun getListRepos(reposUrl: String): List<RepositoryItem> =
        apiService.getUserRepos(reposUrl)

    override suspend fun getListOrganizations(organizationUrl: String): List<OrganizationItem> =
        apiService.getUserOrganizations(organizationUrl)
}