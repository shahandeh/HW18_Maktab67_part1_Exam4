package com.example.exam4.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.exam4.date.model.UserListModelItem

class DiffCallback: DiffUtil.ItemCallback<UserListModelItem>() {
    override fun areItemsTheSame(oldItem: UserListModelItem, newItem: UserListModelItem) = oldItem._id == newItem._id

    override fun areContentsTheSame(oldItem: UserListModelItem, newItem: UserListModelItem) = oldItem == newItem
}