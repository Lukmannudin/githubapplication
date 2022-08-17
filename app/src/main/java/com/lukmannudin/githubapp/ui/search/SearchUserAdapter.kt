package com.lukmannudin.githubapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.lukmannudin.githubapp.common.EspressoIdlingResource
import com.lukmannudin.githubapp.data.model.User
import com.lukmannudin.githubapp.databinding.ItemUserBinding

class SearchUserAdapter : ListAdapter<User, SearchUserViewHolder>(SearchUserDiffUtilCallback()) {

    lateinit var onClickItemListener: ((User) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        return SearchUserViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
        holder.bindItem(currentList[position], onClickItemListener)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun addAll(users: List<User>) {
        val items = mutableListOf<User>()
        items.addAll(currentList)
        items.addAll(users)
        items.sortBy { user ->
            user.login
        }
        submitList(items.distinct())
        EspressoIdlingResource.decrement()
    }

    fun clearAndAddAll(users: List<User>){
        submitList(users)
        EspressoIdlingResource.decrement()
    }

    fun clear() {
        submitList(mutableListOf())
    }

}