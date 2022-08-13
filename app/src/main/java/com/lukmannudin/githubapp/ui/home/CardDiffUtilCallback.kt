package com.lukmannudin.githubapp.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.lukmannudin.githubapp.data.User

/**
 * Created by Lukmannudin on 12/08/22
 */

class CardDiffUtilCallback: DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}