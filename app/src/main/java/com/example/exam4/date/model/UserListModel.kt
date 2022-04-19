package com.example.exam4.date.model

import androidx.room.Entity
import androidx.room.PrimaryKey

class UserListModel : ArrayList<UserListModelItem>()

@Entity(tableName = "userModel")
data class UserListModelItem(
    @PrimaryKey
    val _id: String,
    val firstName: String,
    val lastName: String,
    val nationalCode: String
)