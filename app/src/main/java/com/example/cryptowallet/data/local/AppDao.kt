package com.example.cryptowallet.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptowallet.data.model.UserModel

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(model: UserModel)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    suspend fun getUser(): UserModel

    @Query("SELECT COUNT(*) FROM user_table")
    suspend fun getSizeUserTable(): Int

    @Query("DELETE FROM user_table")
    suspend fun deleteUserTable()
}

