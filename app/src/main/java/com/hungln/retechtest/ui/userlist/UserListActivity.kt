package com.hungln.retechtest.ui.userlist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hungln.retechtest.R
import com.hungln.retechtest.data.network.response.User
import com.hungln.retechtest.databinding.ActivityUserListBinding
import com.hungln.retechtest.ui.userdetail.UserDetailActivity
import com.hungln.retechtest.util.AppConstants
import com.hungln.retechtest.util.NetworkResult
import com.hungln.retechtest.util.makeGone
import com.hungln.retechtest.util.makeVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserListBinding
    private val userListViewModel: UserListViewModel by viewModel()
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObjects()
        setupEvents()
    }

    private fun setupObjects() {
        userListViewModel.getListUsers()

        userListAdapter = UserListAdapter(this@UserListActivity) {
            onUserClickListener(it)
        }
        binding.apply {
            rvUserList.layoutManager = LinearLayoutManager(this@UserListActivity)
            rvUserList.adapter = userListAdapter

            swipeRefreshList.setOnRefreshListener {
                userListViewModel.getListUsers()
            }
        }

    }

    private fun setupEvents() {
        userListViewModel.listUserResult.observe(this) { usersResult ->
            when (usersResult) {
                is NetworkResult.Loading -> {
                    if (!binding.swipeRefreshList.isRefreshing) {
                        binding.pbLoading.makeVisible()
                    }
                }
                is NetworkResult.Success -> {
                    binding.apply {
                        pbLoading.makeGone()
                        swipeRefreshList.isRefreshing = false
                    }
                    userListAdapter.submitList(usersResult.data)
                }

                is NetworkResult.Error -> {
                    binding.apply {
                        pbLoading.makeGone()
                        rvUserList.makeGone()
                        swipeRefreshList.isRefreshing = false
                    }
//                    Toast.makeText(this, usersResult.message, Toast.LENGTH_SHORT).show()
                    loadDataFromCache()
                }
            }
        }
    }

    private fun onUserClickListener(user: User) {
        Intent(this@UserListActivity, UserDetailActivity::class.java).also {
            it.putExtra(AppConstants.LOGIN_NAME, user.login)
            startActivity(it)
        }
    }

    private fun loadDataFromCache() {
        userListViewModel.listLocalUsers.observe(this) { database ->
            if (database.isNotEmpty()) {
                userListAdapter.submitList(database)
            } else {
                Toast.makeText(this, getString(R.string.empty_data), Toast.LENGTH_SHORT).show()
            }
        }
    }
}