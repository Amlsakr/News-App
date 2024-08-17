package com.example.newsreaderapp.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.newsreaderapp.data.sources.NewsRemoteMediator
import com.example.newsreaderapp.data.sources.local.NewsDatabase
import com.example.newsreaderapp.data.sources.local.model.ArticleEntity
import com.example.newsreaderapp.data.sources.remote.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsPager {

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideNewsPager(newsDatabase: NewsDatabase, newsApi: NewsApi): Pager<Int, ArticleEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = NewsRemoteMediator(
                newsDatabase = newsDatabase,
                newsApi = newsApi
            ),
            pagingSourceFactory = {
                newsDatabase.getNewsDao().pagingSource()
            }
        )
    }
}