package com.example.newsreaderapp.data.sources

import android.util.Log
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
            Log.e("NewsRemoteMediator", "start")
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    Log.e("NewsRemoteMediator", "REFRESH")
                   0
                }  // Reset to page 1 on refresh
                LoadType.PREPEND -> {
                    Log.e("NewsRemoteMediator", "PREPEND")
                    // Not supported for news since pagination is one-directional
                    return MediatorResult.Success(endOfPaginationReached = true)

                }

                LoadType.APPEND -> {
                    Log.e("NewsRemoteMediator", "AppEND")
                    val lastItem = state.lastItemOrNull()
                    Log.e("NewsRemoteMediator", "lastItem ${lastItem.toString()}")
                    if (lastItem == null) {
                        Log.e("NewsRemoteMediator", "lastItem = null")
                        1
                    } else {
                        Log.e("page number", "Append page number ${  (lastItem.id / state.config.pageSize) + 1}")
                        Log.e("page number", "pages.size ${   state.pages.size}")
                        (lastItem.id / state.config.pageSize) + 1


                    }
                }
            }
            Log.e("NewsRemoteMediator", "before execute response")
            val response = newsApi.getTopHeadlines(
                page = page
            )

            Log.e("NewsRemoteMediator", "after execute response")

            if (response == null)
                Log.e("NewsRemoteMediator", "response is null")
            else {
                Log.e("NewsRemoteMediator", "response${response.status}")
                Log.e("NewsRemoteMediator", "response is exist")
              //  Log.e("NewsRemoteMediator", "response${response.articleDtos.size}")
            }

            newsDatabase.withTransaction {
                Log.e("NewsRemoteMediator", "withTransaction start")
                if (loadType == LoadType.REFRESH) {
                    newsDao.clearAll()
                }
                Log.e("NewsRemoteMediator", "withTransaction news dao clear")
                Log.e("NewsRemoteMediator", "size ${response.articles.size}")
                val beerEntities = response.articles.map { it.toArticleEntity() }
                Log.e("NewsRemoteMediator", "size ${response.articles.size}")

                newsDao.insertAll(beerEntities)
                Log.e("NewsRemoteMediator", "insertion happen")
            }
            MediatorResult.Success(
                endOfPaginationReached = response.articles.isEmpty()
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