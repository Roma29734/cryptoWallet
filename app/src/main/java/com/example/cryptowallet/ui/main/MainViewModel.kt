package com.example.cryptowallet.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptowallet.data.AppRepository
import com.example.cryptowallet.data.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {
    private var _userData = MutableLiveData<UserModel>()
    val userData get() = _userData

    fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            _userData.postValue(repository.getUser())
        }
    }

    private suspend fun getSizeUserTable(): Int = repository.getSizeUserTable()

    private suspend fun insertUser(model: UserModel) {
        repository.insertUser(model)
    }

    fun checkUser() {
        viewModelScope.launch(Dispatchers.IO) {
            if (getSizeUserTable() == 0) {
                val model = UserModel(id = 0,
                    name = "Andrey",
                    phoneNumber = "+73923750923",
                    password = 1234)
                insertUser(model)
            }
        }
    }

}