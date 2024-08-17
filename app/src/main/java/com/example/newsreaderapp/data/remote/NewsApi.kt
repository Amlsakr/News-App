package com.example.newsreaderapp.data.remote

import com.example.newsreaderapp.BuildConfig
import com.example.newsreaderapp.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
    }

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): NewsResponse
}