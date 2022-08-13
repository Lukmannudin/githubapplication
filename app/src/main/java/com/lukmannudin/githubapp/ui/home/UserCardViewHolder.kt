package com.lukmannudin.githubapp.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lukmannudin.githubapp.common.showIfNotEmpty
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.databinding.ItemCardBinding

class UserCardViewHolder(
    private val itemCardBinding: ItemCardBinding
) : RecyclerView.ViewHolder(itemCardBinding.root) {
    fun bindItem(user: User) {
        with(itemCardBinding) {
            Glide.with(root.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(ivThumbnailUser)

            if (user.login.isNotEmpty()) {
                tvName.showIfNotEmpty(user.login)
                tvCompany.showIfNotEmpty(user.company)
                tvEmail.showIfNotEmpty(user.email)
                tvLocation.showIfNotEmpty(user.location)
                tvTwitterUsername.showIfNotEmpty(user.twitterUsername)
            }
        }
    }
}