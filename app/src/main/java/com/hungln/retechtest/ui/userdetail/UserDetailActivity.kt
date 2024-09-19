package com.hungln.retechtest.ui.userdetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hungln.retechtest.R
import com.hungln.retechtest.data.network.response.OrganizationItem
import com.hungln.retechtest.data.network.response.RepositoryItem
import com.hungln.retechtest.data.network.response.User
import com.hungln.retechtest.databinding.ActivityUserDetailBinding
import com.hungln.retechtest.util.AppConstants
import com.hungln.retechtest.util.NetworkResult
import com.hungln.retechtest.util.makeBoldFollowersText
import com.hungln.retechtest.util.makeGone
import com.hungln.retechtest.util.makeVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private var userLogin: String? = null
    private val userDetailViewModel: UserDetailViewModel by viewModel()
    private lateinit var userReposAdapter: UserReposAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObjects()
        setupEvents()
    }

    private fun setupObjects() {
        userLogin = intent.getStringExtra(AppConstants.LOGIN_NAME)

        userDetailViewModel.getUserDetails(userLogin ?: "")
        userDetailViewModel.getUserRepos(userLogin ?: "")
        userDetailViewModel.getUserOrganizations(userLogin ?: "")

        binding.apply {
            swipeRefreshUserDetail.setOnRefreshListener {
                userDetailViewModel.getUserDetails(userLogin ?: "")
                userDetailViewModel.getUserRepos(userLogin ?: "")
                userDetailViewModel.getUserOrganizations(userLogin ?: "")
            }
            ivBackToListUser.setOnClickListener {
                finish()
            }
            etUserCompany.isEnabled = false
            userReposAdapter = UserReposAdapter(this@UserDetailActivity) {

            }
            rvRepos.layoutManager =
                LinearLayoutManager(this@UserDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            rvRepos.adapter = userReposAdapter

            svUserDetail.viewTreeObserver.addOnScrollChangedListener {
                val canScrollUp = svUserDetail.canScrollVertically(-1)
                swipeRefreshUserDetail.isEnabled = !canScrollUp
            }
        }


    }

    private fun setupEvents() {
        userDetailViewModel.userDetailsResult.observe(this) { userResult ->
            handleUserDetailsResult(userResult)
        }

        userDetailViewModel.userReposResult.observe(this) { userRepos ->
            handleUserReposResult(userRepos)
        }

        userDetailViewModel.userOrganizationsResult.observe(this) { userOrganizations ->
            handleUserOrganizationsResult(userOrganizations)
        }

        userDetailViewModel.localUserDetailsResult.observe(this) { localUser ->
            setupUserDetailData(localUser.data)
        }

        userDetailViewModel.repositoriesWithOwner.observe(this) { localRepos ->
            setupUserRepos(localRepos)
        }
    }

    private fun setupUserDetailData(userResult: User?) {
        binding.apply {
            userResult?.let {
                Glide.with(this@UserDetailActivity).load(userResult.avatarUrl)
                    .apply(RequestOptions().transform(RoundedCorners(50)))
                    .placeholder(R.drawable.placeholder).into(ivUserAvatar)
                tvUsername.text = it.name
                tvUserLogin.text = it.login
                etUserCompany.setText(it.company)
                tvUserBio.text = it.bio
                tvEducation.text = if(it.blog.isNullOrEmpty()) getString(R.string.blog_hidden) else it.blog
                tvLocation.text = if(it.location.isNullOrEmpty()) getString(R.string.location_hidden) else it.location
                tvBlog.text = if (it.email.isNullOrEmpty()) getString(R.string.no_blog) else it.email
                tvTwitter.text = if(it.twitterUsername.isNullOrEmpty()) getString(R.string.no_twitter_username) else it.twitterUsername
                tvFollower.text = makeBoldFollowersText(it.followers)
                tvFollowing.text = makeBoldFollowersText(it.following)
                tvRepositoriesCount.text = it.publicRepos.toString()
                tvGistsCount.text = it.publicGists.toString()

                if (it.company.isNullOrEmpty()) {
                    etUserCompany.makeGone()
                }
            }
        }
    }

    private fun handleUserDetailsResult(userResult: NetworkResult<User>?) {
        when (userResult) {
            is NetworkResult.Loading -> {
                if (!binding.swipeRefreshUserDetail.isRefreshing) {
                    binding.pbLoading.makeVisible()
                }
            }

            is NetworkResult.Success -> {
                binding.apply {
                    pbLoading.makeGone()
                    swipeRefreshUserDetail.isRefreshing = false
                }
                setupUserDetailData(userResult.data)
            }

            is NetworkResult.Error -> {
                binding.apply {
                    pbLoading.makeGone()
                    swipeRefreshUserDetail.isRefreshing = false
                    userDetailViewModel.getLocalUserDetail(userLogin ?: "")
                }
            }

            else -> {}
        }
    }

    private fun handleUserReposResult(userRepos: NetworkResult<List<RepositoryItem>>?) {
        when (userRepos) {
            is NetworkResult.Loading -> {
                if (!binding.swipeRefreshUserDetail.isRefreshing) {
                    binding.pbLoading.makeVisible()
                }
            }

            is NetworkResult.Success -> {
                binding.apply {
                    pbLoading.makeGone()
                    swipeRefreshUserDetail.isRefreshing = false
                }
                setupUserRepos(userRepos.data)
            }

            is NetworkResult.Error -> {
                binding.apply {
                    pbLoading.makeGone()
                    swipeRefreshUserDetail.isRefreshing = false
                    userDetailViewModel.getRepositoriesWithOwner(login = userLogin ?: "")
                }
            }

            else -> Toast.makeText(this, userRepos?.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleUserOrganizationsResult(userOrganization: NetworkResult<List<OrganizationItem>>?) {
        when (userOrganization) {
            is NetworkResult.Loading -> {
                if (!binding.swipeRefreshUserDetail.isRefreshing) {
                    binding.pbLoading.makeVisible()
                }
            }

            is NetworkResult.Success -> {
                binding.apply {
                    pbLoading.makeGone()
                    swipeRefreshUserDetail.isRefreshing = false
                }
                setupUserOrganizations(userOrganization.data)
            }

            is NetworkResult.Error -> {
                binding.apply {
                    pbLoading.makeGone()
                    swipeRefreshUserDetail.isRefreshing = false
                }
            }

            else -> Toast.makeText(this, userOrganization?.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupUserRepos(userReposData: List<RepositoryItem>?) {
        userReposAdapter.submitList(userReposData)
    }

    private fun setupUserOrganizations(userOrganization: List<OrganizationItem>?) {
        binding.tvOrganizationsCount.text = userOrganization?.size.toString()
    }

}