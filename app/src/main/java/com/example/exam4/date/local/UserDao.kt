package com.example.exam4.date.local

import androidx.room.*
import com.example.exam4.date.model.UserListModelItem
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUserOnDataBase(user: UserListModelItem)

    @Query("SELECT * FROM userModel")
    fun getUserListFromDataBase(): Flow<List<UserListModelItem>>

    @Delete
    suspend fun deleteUser(user: UserListModelItem)

}