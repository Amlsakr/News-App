package com.example.newsreaderapp.domain.repository

import androidx.paging.PagingData
import com.example.newsreaderapp.common.utill.Resource
import com.example.newsreaderapp.data.sources.remote.model.NewsResponse
import com.example.newsreaderapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getTopHeadLines(): Flow<PagingData<Article>>

  suspend  fun getTopHeadLinesByCategory(category: String): Resource<NewsResponse>
}