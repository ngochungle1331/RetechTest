package com.hungln.retechtest.ui.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hungln.retechtest.data.network.response.User
import com.hungln.retechtest.data.source.UserRepository
import com.hungln.retechtest.util.NetworkResult
import kotlinx.coroutines.launch

class UserListViewModel(private val userRepository: UserRepository): ViewModel() {

    val listLocalUsers: LiveData<List<User>> = userRepository.local.getAllUsers().asLiveData()

    private val _listUsersResult = MutableLiveData<NetworkResult<List<User>>>()
    val listUserResult: LiveData<NetworkResult<List<User>>>
        get() = _listUsersResult

    fun getListUsers() {
        viewModelScope.launch {
            _listUsersResult.postValue(NetworkResult.Loading())

            try {
                val users = userRepository.getListUsers()
                _listUsersResult.postValue(NetworkResult.Success(users))

                offlineCacheUserList(users)
            } catch (e: Exception) {
                _listUsersResult.postValue(NetworkResult.Error("Error: ${e.message}"))
            }
        }
    }

    private fun offlineCacheUserList(users: List<User>) {
        viewModelScope.launch {
            userRepository.local.insertAllUsers(users)
        }
    }
}