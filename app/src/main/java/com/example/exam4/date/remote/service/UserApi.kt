package com.example.exam4.date.remote.service

import com.example.exam4.date.model.UserListModel
import com.example.exam4.date.model.UserListModelItem
import com.example.exam4.date.model.UserModel
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserApi {

    // http://papp.ir/api/v1/users?

    @GET("users?")
    suspend fun getUserList(): UserListModel

    @POST("users")
    suspend fun createUser(@Body user: UserModel) : String

    @Multipart
    @POST("users/{id}/image")
    suspend fun uploadImage(
        @Path("id") id: String,
        @Part image: MultipartBody.Part
    ): Any

}