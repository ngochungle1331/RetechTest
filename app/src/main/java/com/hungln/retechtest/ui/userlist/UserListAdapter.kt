package com.hungln.retechtest.ui.userlist

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
import com.hungln.retechtest.data.network.response.User
import com.hungln.retechtest.databinding.LayoutUserItemBinding


class UserListAdapter(private val context: Context, private val itemClick: (User) -> Unit) :
    ListAdapter<User, UserListAdapter.UserListViewHolder>(
        DIFF_UTIL
    ) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    inner class UserListViewHolder(private val binding: LayoutUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(user: User?) {
            user?.apply {
                binding.apply {
                    Glide.with(context).load(user.avatarUrl)
                        .apply(RequestOptions().transform(RoundedCorners(50)))
                        .placeholder(R.drawable.placeholder).into(ivUser)

                    tvUser.text = user.login
                    if (user.type == "User") {
                        tvUserType.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_person,
                            0,
                            0,
                            0
                        )
                    } else {
                        tvUserType.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_organization_detail,
                            0,
                            0,
                            0
                        )
                    }
                    tvUserType.text = user.type

                    root.setOnClickListener {
                        itemClick.invoke(user)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutUserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}