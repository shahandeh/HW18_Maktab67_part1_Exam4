package com.example.exam4.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam4.date.Repository
import com.example.exam4.date.model.UserListModelItem
import com.example.exam4.date.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var _userListFromServer: MutableStateFlow<List<UserListModelItem>> =
        MutableStateFlow(emptyList())

    val userListFromServer: StateFlow<List<UserListModelItem>> = _userListFromServer

    lateinit var generatedTokenFromServer: String

    val userListFromDataBase: StateFlow<List<UserListModelItem>> =
        repository.getUserListFromDataBase().stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )

    fun uploadImage(id: String, image: ByteArray) {
        launch {
            val create = MultipartBody.create(MediaType.parse("image/*"), image)
            val body = MultipartBody.Part.createFormData("image", "image.jpg", create)
            repository.uploadImage(id, body)
        }
    }

    fun createUserOnServer(firstName: String, lastName: String, nationalCode: String) {
        if (firstName.isNotBlank() && lastName.isNotBlank() && nationalCode.isNotBlank()) {
            launch {
                val generatedId = repository.createUserOnServer(
                    UserModel(firstName, lastName, nationalCode)
                )
                generatedTokenFromServer = generatedId
            }
            getUserListFromServer()
        }
    }

    fun getUserListFromServer() {
            try {
                launch {
                    val response = repository.getUserListFromServer()
                    _userListFromServer.emit(response)
                }
            } catch (e: Exception) {
                e.stackTraceToString()
            }
    }

    fun createUserOnDataBase(position: Int) {
        launch { repository.createUserOnDataBase(userListFromServer.value[position]) }
    }

    fun deleteUserFromDataBase(position: Int) {
        launch { repository.deleteUserFromDataBase(userListFromDataBase.value[position]) }
    }


    fun editUserInDataBase(
        position: Int,
        firstName: String,
        lastName: String,
        nationalCode: String
    ) {
        if (firstName.isNotBlank() && lastName.isNotBlank() && nationalCode.isNotBlank()) {
            val temp = UserListModelItem(
                userListFromDataBase.value[position]._id, firstName, lastName, nationalCode
            )
            launch { repository.createUserOnDataBase(temp) }

            createUserOnServer(firstName, lastName, nationalCode)
        }
    }

    private fun ViewModel.launch(fn: suspend () -> Unit) {
        viewModelScope.launch {
            fn()
        }
    }

}

