package com.example.cryptowallet.ui.start

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
class StarViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {

    private var _userData = MutableLiveData<UserModel>()
    val userData get() = _userData

    fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            _userData.postValue(repository.getUser())
        }
    }

    suspend fun getSizeUserTable(): Int = repository.getSizeUserTable()
}