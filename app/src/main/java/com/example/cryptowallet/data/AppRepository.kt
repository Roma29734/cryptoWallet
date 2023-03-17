package com.example.cryptowallet.data

import com.example.cryptowallet.data.local.AppDao
import com.example.cryptowallet.data.model.UserModel
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val dao: AppDao
) {

    suspend fun insertUser(model: UserModel) {
        dao.insertUser(model)
    }

    suspend fun getUser(): UserModel {
        return dao.getUser()
    }

    suspend fun getSizeUserTable(): Int {
        return dao.getSizeUserTable()
    }

    suspend fun deleteUserTable() {
        dao.deleteUserTable()
    }
}