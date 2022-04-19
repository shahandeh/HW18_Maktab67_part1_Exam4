package com.example.exam4.ui.local

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exam4.databinding.RecyclerViewSampleBinding
import com.example.exam4.date.model.UserListModelItem
import com.example.exam4.ui.DiffCallback

class DataBaseFragmentAdapter(): ListAdapter<UserListModelItem, DataBaseFragmentAdapter.AppViewHolder>(DiffCallback()) {

    class AppViewHolder(
        private val binding: RecyclerViewSampleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserListModelItem) {
            binding.textViewRecyclerViewSample.text = user.firstName + user.lastName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder =
        AppViewHolder(RecyclerViewSampleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}