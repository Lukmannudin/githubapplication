package com.lukmannudin.githubapp.ui.repository

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.lukmannudin.githubapp.data.Repo
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.databinding.ItemRepositoryBinding

class RepositoryAdapter : ListAdapter<Repo, RepositoryViewHolder>(RepositoryDiffUtilCallback()) {

    var user: User? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bindItem(currentList[position], user)
    }

    fun addAll(repositories: List<Repo>) {
        submitList(repositories)
    }

    fun clear() {
        submitList(mutableListOf())
    }

}