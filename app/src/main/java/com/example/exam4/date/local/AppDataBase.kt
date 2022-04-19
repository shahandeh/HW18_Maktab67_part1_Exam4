package com.example.exam4.date.local

import android.content.Context
import androidx.room.*
import com.example.exam4.date.model.UserListModelItem

@Database(entities = [UserListModelItem::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}