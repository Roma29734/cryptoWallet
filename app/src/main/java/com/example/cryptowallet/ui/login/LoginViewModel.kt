package com.example.cryptowallet.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptowallet.data.AppRepository
import com.example.cryptowallet.data.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {

    fun createUser(name: String, phone: String, password: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val model =
                UserModel(id = 0, name = name, phoneNumber = phone, password = password)
            repository.insertUser(model)
        }
    }
}