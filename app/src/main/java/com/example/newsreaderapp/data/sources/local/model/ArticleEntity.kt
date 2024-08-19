package com.example.newsreaderapp.data.sources.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class ArticleEntity(
    val id: Int = 0,
    val author: String,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val source: String?,
    @PrimaryKey
    val title: String,
    val url: String,
    val urlToImage: String?
)
