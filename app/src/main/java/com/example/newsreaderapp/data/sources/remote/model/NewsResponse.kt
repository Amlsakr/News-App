package com.example.newsreaderapp.data.sources.remote.model

data class ArticleDto(
    val author: String,
    val content: String,
    val description: String?,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String?
)

data class NewsResponse(
    val articleDtos: List<ArticleDto>,
    val status: String,
    val totalResults: Int
)

data class Source(
    val id: String,
    val name: String
)