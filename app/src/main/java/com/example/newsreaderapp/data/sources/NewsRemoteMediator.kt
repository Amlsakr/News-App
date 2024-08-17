package com.example.newsreaderapp.data.sources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.newsreaderapp.data.sources.local.NewsDatabase
import com.example.newsreaderapp.data.sources.local.model.ArticleEntity
import com.example.newsreaderapp.data.sources.mappers.toArticleEntity
import com.example.newsreaderapp.data.sources.remote.NewsApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDatabase: NewsDatabase
) : RemoteMediator<Int, ArticleEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        val newsDao = newsDatabase.getNewsDao()
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1  // Reset to page 1 on refresh
                LoadType.PREPEND -> {
                    // Not supported for news since pagination is one-directional
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }
            val response = newsApi.getTopHeadlines(
                page = page
            )
            newsDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    newsDao.clearAll()
                }
                val beerEntities = response.articleDtos.map { it.toArticleEntity() }
                newsDao.insertAll(beerEntities)
            }
            MediatorResult.Success(
                endOfPaginationReached = response.articleDtos.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}