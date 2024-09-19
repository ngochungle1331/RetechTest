package com.hungln.retechtest.ui.userdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hungln.retechtest.data.network.response.OrganizationItem
import com.hungln.retechtest.data.network.response.RepositoryItem
import com.hungln.retechtest.data.network.response.RepositoryWithOwner
import com.hungln.retechtest.data.network.response.User
import com.hungln.retechtest.data.source.UserRepository
import com.hungln.retechtest.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class UserDetailViewModel(private val userRepository: UserRepository): ViewModel() {

    private val _userDetailsResult = MutableLiveData<NetworkResult<User>>()
    val userDetailsResult: LiveData<NetworkResult<User>>
        get() = _userDetailsResult

    fun getUserDetails(loginUser: String) {
        viewModelScope.launch {
            _userDetailsResult.postValue(NetworkResult.Loading())
            try {
                val userDetails = userRepository.getUserDetails(loginUser)
                _userDetailsResult.postValue(NetworkResult.Success(userDetails))
                updateUser(userDetails)
            } catch (e: Exception) {
                _userDetailsResult.postValue(NetworkResult.Error("Error: ${e.message}"))
            }
        }
    }

    private val _userReposResult = MutableLiveData<NetworkResult<List<RepositoryItem>>>()
    val userReposResult: LiveData<NetworkResult<List<RepositoryItem>>>
        get() = _userReposResult

    fun getUserRepos(loginName: String) {
        viewModelScope.launch {
            _userReposResult.postValue(NetworkResult.Loading())
            try {
                val fullUrl = "https://api.github.com/users/$loginName/repos"
                val repos = userRepository.getListRepos(fullUrl)
                _userReposResult.postValue(NetworkResult.Success(repos))
                insertRepositories(repos)
            } catch (e: Exception) {
                _userReposResult.postValue(NetworkResult.Error("Error: ${e.message}"))
            }
        }
    }

    private val _userOrganizationsResult = MutableLiveData<NetworkResult<List<OrganizationItem>>>()
    val userOrganizationsResult: LiveData<NetworkResult<List<OrganizationItem>>>
        get() = _userOrganizationsResult

    fun getUserOrganizations(loginName: String) {
        viewModelScope.launch {
            _userOrganizationsResult.postValue(NetworkResult.Loading())
            try {
                val fullUrl = "https://api.github.com/users/$loginName/orgs"
                val organizations = userRepository.getListOrganizations(fullUrl)
                _userOrganizationsResult.postValue(NetworkResult.Success(organizations))
            } catch (e: Exception) {
                _userOrganizationsResult.postValue(NetworkResult.Error("Error: ${e.message}"))
            }
        }
    }

    private val _localUserDetailsResult = MutableLiveData<NetworkResult<User?>>()
    val localUserDetailsResult: LiveData<NetworkResult<User?>>
        get() = _localUserDetailsResult

    fun getLocalUserDetail(login: String) {
        viewModelScope.launch {
            _localUserDetailsResult.postValue(NetworkResult.Loading())
            try {
                val localUser = userRepository.getUserByLogin(login)
                _localUserDetailsResult.postValue(NetworkResult.Success(localUser))
            } catch (e: Exception) {
                _localUserDetailsResult.postValue(NetworkResult.Error("Error: ${e.message}"))
            }
        }
    }

    private fun updateUser(user: User) {
        viewModelScope.launch {
            val isSuccess = userRepository.replaceUser(user)
            if (isSuccess) {
                Log.d("UserListViewModel", "User replacement successful!")
            } else {
                Log.e("UserListViewModel", "User replacement failed.")
            }
        }
    }

    private val _repositoriesWithOwner = MutableLiveData<List<RepositoryItem>>()
    val repositoriesWithOwner: LiveData<List<RepositoryItem>>
        get() = _repositoriesWithOwner

    fun insertRepositories(repositories: List<RepositoryItem>) {
        viewModelScope.launch {
            try {
                val isSuccess = userRepository.insertRepositories(repositories)
                if (isSuccess) {
                    Log.e("UserRepositoryVM", "Repositories inserted successfully")
                } else {
                    Log.e("UserRepositoryVM", "Failed to insert repositories")
                }
            } catch (e: Exception) {
                Log.e("UserRepositoryVM", "Error inserting repositories: ${e.message}")
            }
        }
    }

    fun getRepositoriesWithOwner(login: String) {
        viewModelScope.launch {
            userRepository.getRepositoriesWithOwner(login)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Log.e("UserRepositoryVM", "Error fetching repositories: ${e.message}")
                }
                .collect { repositoriesWithOwner ->
                    val repositoryItems = repositoriesWithOwner.map { it.repository }
                    _repositoriesWithOwner.postValue(repositoryItems)
                }
        }
    }

}