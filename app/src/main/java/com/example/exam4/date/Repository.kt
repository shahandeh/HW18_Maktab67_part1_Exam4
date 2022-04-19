package com.example.exam4.date

import com.example.exam4.date.local.AppLocalDataSource
import com.example.exam4.date.model.UserListModelItem
import com.example.exam4.date.model.UserModel
import com.example.exam4.date.remote.AppRemoteDataSource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val localDataSource: AppLocalDataSource,
    private val remoteDataSource: AppRemoteDataSource
) {

    suspend fun getUserListFromServer() = remoteDataSource.getUserList()

    suspend fun uploadImage(id: String, image: MultipartBody.Part){
        remoteDataSource.uploadImage(id, image)
    }

    suspend fun createUserOnServer(user: UserModel) = remoteDataSource.createUserOnServer(user)

    suspend fun createUserOnDataBase(user: UserListModelItem){
        localDataSource.createUserOnDataBase(user)
    }

    fun getUserListFromDataBase() = localDataSource.getUserListFromDataBase()

    suspend fun deleteUserFromDataBase(user: UserListModelItem){
        localDataSource.deleteUser(user)
    }

}