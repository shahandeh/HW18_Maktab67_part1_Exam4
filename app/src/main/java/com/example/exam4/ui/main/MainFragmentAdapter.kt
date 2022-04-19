package com.example.exam4.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exam4.databinding.RecyclerViewSampleBinding
import com.example.exam4.date.model.UserListModelItem
import com.example.exam4.ui.DiffCallback

class MainFragmentAdapter(
    private val itemClick: (position: Int) -> Unit
): ListAdapter<UserListModelItem, MainFragmentAdapter.AppViewHolder>(DiffCallback()) {

    inner class AppViewHolder(
        private val binding: RecyclerViewSampleBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(user: UserListModelItem){
            binding.textViewRecyclerViewSample.text = user.firstName + user.lastName
            binding.root.setOnClickListener {
                itemClick(absoluteAdapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AppViewHolder(RecyclerViewSampleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}