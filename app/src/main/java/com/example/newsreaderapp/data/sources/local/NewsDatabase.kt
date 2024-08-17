package com.example.newsreaderapp.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsreaderapp.data.sources.local.model.ArticleEntity

@Database(
    version = 1,
    exportSchema = false,
    entities = [ArticleEntity::class]
)
@TypeConverters()
abstract class NewsDatabase : RoomDatabase() {

    abstract fun getNewsDao(): NewsDao

    companion object {
        const val DATA_BASE_NAME = "news_database"
    }
}