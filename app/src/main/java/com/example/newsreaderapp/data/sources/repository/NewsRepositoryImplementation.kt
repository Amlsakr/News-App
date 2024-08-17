package com.example.newsreaderapp.data.sources.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsreaderapp.data.sources.local.NewsDatabase
import com.example.newsreaderapp.data.sources.local.model.ArticleEntity
import com.example.newsreaderapp.data.sources.mappers.toArticle
import com.example.newsreaderapp.data.sources.remote.NewsApi
import com.example.newsreaderapp.domain.model.Article
import com.example.newsreaderapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImplementation @Inject constructor(
    private val newsPager: Pager<Int, ArticleEntity>,
    private val newsDatabase: NewsDatabase,
    private val newsApi: NewsApi,
) : NewsRepository {
    override  fun getTopHeadLines(): Flow<PagingData<Article>> {
        return newsPager.flow.map { pagingData ->
            pagingData.map { it.toArticle() }
        }
    }
}