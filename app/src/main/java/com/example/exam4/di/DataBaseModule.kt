package com.example.exam4.di

import android.content.Context
import androidx.room.*
import com.example.exam4.date.local.AppDataBase
import com.example.exam4.date.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext context: Context): AppDataBase = Room.databaseBuilder(
        context.applicationContext,
        AppDataBase::class.java,
        "user_database"
    ).build()

    @Singleton
    @Provides
    fun userDao(dataBase: AppDataBase): UserDao = dataBase.userDao()

}