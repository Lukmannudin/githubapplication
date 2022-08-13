package com.lukmannudin.githubapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.databinding.ItemCardBinding

class UserCardAdapter : ListAdapter<User, UserCardAdapter.ViewHolder>(CardDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCardBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

    class ViewHolder(
        private val itemCardBinding: ItemCardBinding
    ) : RecyclerView.ViewHolder(itemCardBinding.root) {
        fun bindItem(user: User) {
            with(itemCardBinding) {
                Glide.with(root.context)
                    .load(user.avatarUrl)
                    .into(ivThumbnailUser)

                tvCompany.text = user.company
                tvEmail.text = user.email
                tvName.text = user.login
                tvLocation.text = user.location
            }
        }
    }
}