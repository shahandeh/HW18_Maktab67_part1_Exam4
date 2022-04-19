package com.example.exam4.date.remote

import com.example.exam4.date.model.UserModel
import com.example.exam4.date.remote.service.UserApi
import okhttp3.MultipartBody
import javax.inject.Inject

class AppRemoteDataSource @Inject constructor(
    private val userApi: UserApi
) {

    suspend fun getUserList() = userApi.getUserList()

    suspend fun createUserOnServer(user: UserModel) = userApi.createUser(user)

    suspend fun uploadImage(id: String, image: MultipartBody.Part) {
        userApi.uploadImage(id, image)
    }
}