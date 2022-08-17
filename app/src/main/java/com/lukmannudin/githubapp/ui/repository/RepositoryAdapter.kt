package com.lukmannudin.githubapp.ui.repository

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.lukmannudin.githubapp.data.model.Repo
import com.lukmannudin.githubapp.data.model.User
import com.lukmannudin.githubapp.databinding.ItemRepositoryBinding

class RepositoryAdapter : ListAdapter<Repo, RepositoryViewHolder>(RepositoryDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bindItem(currentList[position])
    }

    fun addAll(users: List<Repo>) {
        val items = mutableListOf<Repo>()
        items.addAll(currentList)
        items.addAll(users)
        val sortedItems = items.sortedBy { repo ->
            repo.name
        }
        submitList(sortedItems.distinctBy {
            repo -> repo.name
        })
    }

    fun clearAndAddAll(repositories: List<Repo>) {
        submitList(repositories.sortedBy { repo ->
            repo.name
        })
    }

}