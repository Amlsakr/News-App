package com.example.newsreaderapp.domain.repository

import androidx.paging.PagingData
import com.example.newsreaderapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getTopHeadLines(): Flow<PagingData<Article>>
}