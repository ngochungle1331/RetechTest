package com.hungln.retechtest.data.network

import com.hungln.retechtest.data.network.response.OrganizationItem
import com.hungln.retechtest.data.network.response.RepositoryItem
import com.hungln.retechtest.data.network.response.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{ID}")
    suspend fun getUserDetails(
        @Path("ID") login: String
    ): User

    @GET
    suspend fun getUserRepos(@Url fullUrl: String): List<RepositoryItem>

    @GET
    suspend fun getUserOrganizations(@Url fullUrl: String): List<OrganizationItem>
}