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
                LoadType.REFRESH -> {
                    1
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    } else {
                        state.anchorPosition.let { anchorPosition ->
                            anchorPosition?.let { state.closestPageToPosition(it)?.nextKey }
                                ?: 1
                        }
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
                val newsEntities = response?.articles?.map { it.toArticleEntity() }
                newsEntities?.let { newsDao.insertAll(it) }
            }
            MediatorResult.Success(
                endOfPaginationReached = response?.articles?.isEmpty() == true
            )

        } catch (e: IOException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }


}