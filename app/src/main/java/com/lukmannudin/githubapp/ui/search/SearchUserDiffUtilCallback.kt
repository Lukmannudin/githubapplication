package com.lukmannudin.githubapp.ui.search

import androidx.recyclerview.widget.DiffUtil
import com.lukmannudin.githubapp.data.model.User

/**
 * Created by Lukmannudin on 12/08/22
 */

class SearchUserDiffUtilCallback: DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return (oldItem.id == newItem.id) &&
                (oldItem.login == newItem.login) &&
                (oldItem.email == newItem.email)
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }
}