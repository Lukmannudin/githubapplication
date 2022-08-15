package com.lukmannudin.githubapp.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.lukmannudin.githubapp.common.extension.showAsCircle
import com.lukmannudin.githubapp.common.extension.showIfNotEmpty
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.databinding.ItemUserBinding

class SearchUserViewHolder(
    private val itemCardBinding: ItemUserBinding
) : RecyclerView.ViewHolder(itemCardBinding.root) {
    fun bindItem(user: User, onClickItemListener: (User) -> Unit) {
        with(itemCardBinding) {
            if (user.login.isNotEmpty()) {
                ivThumbnailUser.showAsCircle(user.avatarUrl)
                tvName.showIfNotEmpty(user.login)
                tvCompany.showIfNotEmpty(user.company)
                tvEmail.showIfNotEmpty(user.email)
                tvLocation.showIfNotEmpty(user.location)
                tvTwitterUsername.showIfNotEmpty(user.twitterUsername)

                itemCardBinding.root.setOnClickListener {
                    onClickItemListener.invoke(user)
                }
            }
        }
    }
}