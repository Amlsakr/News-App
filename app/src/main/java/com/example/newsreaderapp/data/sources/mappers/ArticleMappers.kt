package com.example.newsreaderapp.data.sources.mappers

import com.example.newsreaderapp.data.sources.local.model.ArticleEntity
import com.example.newsreaderapp.data.sources.remote.model.NewsResponse

import com.example.newsreaderapp.domain.model.Article

fun NewsResponse.ArticleDto.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source.name,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun ArticleEntity.toArticle(): Article {
    return Article(
        author = author,
        content = content ,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

