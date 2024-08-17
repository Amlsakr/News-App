package com.example.newsreaderapp.data

import com.example.newsreaderapp.common.Resource
import com.example.newsreaderapp.data.model.NewsResponse
import com.example.newsreaderapp.data.remote.NewsApi
import com.example.newsreaderapp.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImplementation @Inject constructor(private val newsApi : NewsApi) : NewsRepository {
    override suspend fun getTopHeadLines(): Resource<NewsResponse> {
        return try {
            Resource.Success(data = newsApi.getTopHeadlines())
        }catch (e: Exception){
            e.printStackTrace()
            Resource.Error(message = e.message ?: "Something went wrong")
        }
    }
}