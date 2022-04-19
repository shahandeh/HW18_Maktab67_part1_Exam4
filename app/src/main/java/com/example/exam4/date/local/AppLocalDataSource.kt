package com.example.exam4.date.local

import com.example.exam4.date.model.UserListModelItem
import javax.inject.Inject

class AppLocalDataSource @Inject constructor(private val userDao: UserDao) {

    suspend fun createUserOnDataBase(user: UserListModelItem) {
        userDao.createUserOnDataBase(user)
    }

    fun getUserListFromDataBase() = userDao.getUserListFromDataBase()

    suspend fun deleteUser(user: UserListModelItem) {
        userDao.deleteUser(user)
    }
}