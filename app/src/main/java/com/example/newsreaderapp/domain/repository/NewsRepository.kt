package com.example.newsreaderapp.domain.repository

import com.example.newsreaderapp.common.Resource
import com.example.newsreaderapp.data.model.NewsResponse

interface NewsRepository {

suspend fun getTopHeadLines() : Resource<NewsResponse>
}