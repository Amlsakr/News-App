package com.example.newsreaderapp.data.sources.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsreaderapp.data.sources.local.model.ArticleEntity
import com.example.newsreaderapp.data.sources.remote.model.ArticleDto

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(newsList: List<ArticleEntity>)

    @Query("SELECT * FROM article")
    fun pagingSource(): PagingSource<Int, ArticleEntity>

    @Query("DELETE FROM article")
    suspend fun clearAll()
}