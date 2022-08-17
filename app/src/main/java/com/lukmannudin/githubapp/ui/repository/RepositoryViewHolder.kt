package com.lukmannudin.githubapp.ui.repository

import androidx.recyclerview.widget.RecyclerView
import com.lukmannudin.githubapp.common.extension.getLastUpdatedTimeText
import com.lukmannudin.githubapp.common.extension.showAsCircle
import com.lukmannudin.githubapp.common.extension.showIfNotEmpty
import com.lukmannudin.githubapp.data.model.Repo
import com.lukmannudin.githubapp.data.model.User
import com.lukmannudin.githubapp.databinding.ItemRepositoryBinding


class RepositoryViewHolder(
    private val itemRepositoryBinding: ItemRepositoryBinding
) : RecyclerView.ViewHolder(itemRepositoryBinding.root) {
    fun bindItem(repo: Repo) {
        with(itemRepositoryBinding) {
            if (repo.name.isNotEmpty()) {
                ivThumbnailUser.showAsCircle(repo.ownerAvatarUrl)
                tvRepositoryName.showIfNotEmpty(repo.name)
                tvDescription.showIfNotEmpty(repo.description)
                tvStarLabel.showIfNotEmpty(repo.stargazersCount.toString())
                tvUpdatedTime.showIfNotEmpty(repo.createdAt?.getLastUpdatedTimeText() ?: "")
            }
        }
    }
}