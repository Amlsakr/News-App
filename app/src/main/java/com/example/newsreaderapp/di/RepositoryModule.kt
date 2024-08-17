package com.example.newsreaderapp.di

import com.example.newsreaderapp.data.NewsRepositoryImplementation
import com.example.newsreaderapp.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindNewsRepository(newsRepositoryImplementation: NewsRepositoryImplementation): NewsRepository
}