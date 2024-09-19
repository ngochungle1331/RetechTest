package com.hungln.retechtest.ui.userdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hungln.retechtest.R
import com.hungln.retechtest.data.network.response.RepositoryItem
import com.hungln.retechtest.databinding.LayoutRepoItemBinding

class UserReposAdapter(
    private val context: Context,
    private val itemClick: (RepositoryItem) -> Unit
) :
    ListAdapter<RepositoryItem, UserReposAdapter.UserReposViewHolder>(
        DIFF_UTIL
    ) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<RepositoryItem>() {
            override fun areItemsTheSame(
                oldItem: RepositoryItem,
                newItem: RepositoryItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RepositoryItem,
                newItem: RepositoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    inner class UserReposViewHolder(private val binding: LayoutRepoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(repositoryItem: RepositoryItem?) {
            repositoryItem?.apply {
                binding.apply {
                    Glide.with(context).load(repositoryItem.owner?.avatar_url)
                        .apply(RequestOptions().transform(RoundedCorners(50)))
                        .placeholder(R.drawable.placeholder).into(ivUserAvatar)

                    tvOwnerName.text = repositoryItem.owner?.login
                    tvRepoName.text = repositoryItem.name
                    tvRepoDescription.text = repositoryItem.description

                    tvStarsCount.text = repositoryItem.stargazers_count.toString()
                    tvLanguage.text = repositoryItem.language

                    root.setOnClickListener {
                        itemClick.invoke(repositoryItem)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserReposViewHolder {
        val view = LayoutRepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserReposViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserReposViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}