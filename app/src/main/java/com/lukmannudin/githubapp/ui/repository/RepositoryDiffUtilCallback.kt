package com.lukmannudin.githubapp.ui.repository

import androidx.recyclerview.widget.DiffUtil
import com.lukmannudin.githubapp.data.model.Repo

/**
 * Created by Lukmannudin on 12/08/22
 */

class RepositoryDiffUtilCallback : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return (oldItem.id == newItem.id) &&
                (oldItem.name == newItem.name) &&
                (oldItem.fullName == newItem.fullName) &&
                (oldItem.createdAt == newItem.updateAt) &&
                (oldItem.description == newItem.description)
    }

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return (oldItem.id == newItem.id) &&
                (oldItem.name == newItem.name)
    }

}