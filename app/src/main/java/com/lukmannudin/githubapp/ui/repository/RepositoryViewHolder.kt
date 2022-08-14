package com.lukmannudin.githubapp.ui.repository

import androidx.recyclerview.widget.RecyclerView
import com.lukmannudin.githubapp.common.showAsCircle
import com.lukmannudin.githubapp.common.showIfNotEmpty
import com.lukmannudin.githubapp.data.Repo
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.databinding.ItemRepositoryBinding


class RepositoryViewHolder(
    private val itemRepositoryBinding: ItemRepositoryBinding
) : RecyclerView.ViewHolder(itemRepositoryBinding.root) {
    fun bindItem(repo: Repo, user: User?) {
        with(itemRepositoryBinding) {
            if (repo.name.isNotEmpty()) {
                ivThumbnailUser.showAsCircle(user?.avatarUrl)
                tvRepositoryName.showIfNotEmpty(repo.name)
                tvDescription.showIfNotEmpty(repo.description)
                tvStarLabel.showIfNotEmpty(repo.stargazersCount.toString())
                tvUpdatedTime.showIfNotEmpty(repo.createdAt)
                //todo make updated time ui more experienced
            }
        }
    }
}