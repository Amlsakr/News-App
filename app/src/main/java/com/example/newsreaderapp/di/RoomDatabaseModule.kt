package com.example.newsreaderapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsreaderapp.data.sources.local.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDatabaseModule {
    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext appContext: Context): NewsDatabase =
        Room.databaseBuilder(
            appContext.applicationContext,
            NewsDatabase::class.java,
            NewsDatabase.DATA_BASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
}