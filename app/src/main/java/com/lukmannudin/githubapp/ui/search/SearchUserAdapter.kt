package com.lukmannudin.githubapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.databinding.ItemCardBinding

class SearchUserAdapter : ListAdapter<User, SearchCardViewHolder>(SearchUserDiffUtilCallback()) {

    lateinit var onClickItemListener: ((User) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCardViewHolder {
        return SearchCardViewHolder(
            ItemCardBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: SearchCardViewHolder, position: Int) {
        holder.bindItem(currentList[position], onClickItemListener)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun addAll(users: List<User>) {
        submitList(users)
    }

    fun clear() {
        submitList(mutableListOf())
    }

}