package com.lukmannudin.githubapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.databinding.ItemCardBinding

class UserCardAdapter : ListAdapter<User, UserCardViewHolder>(CardDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCardViewHolder {
        return UserCardViewHolder(
            ItemCardBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: UserCardViewHolder, position: Int) {
        holder.bindItem(currentList[position])
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