package com.example.cryptowallet.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptowallet.data.model.UserModel

@Database(entities = [UserModel::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun appDao(): AppDao
}