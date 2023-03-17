package com.example.cryptowallet.di

import android.content.Context
import androidx.room.Room
import com.example.cryptowallet.data.AppRepository
import com.example.cryptowallet.data.local.AppDao
import com.example.cryptowallet.data.local.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class LocalModule {

    @Provides
    fun provideMovieRepository(dao: AppDao): AppRepository = AppRepository(dao)

    @Provides
    fun provideMovieDao(appDataBase: AppDataBase): AppDao = appDataBase.appDao()

    @Provides
    @Singleton
    fun provideMovieDataBase(@ApplicationContext appContext: Context): AppDataBase =
        Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "user_table"
        ).build()
}