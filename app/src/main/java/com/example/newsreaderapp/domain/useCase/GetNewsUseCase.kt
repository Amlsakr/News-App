package com.example.newsreaderapp.domain.useCase

import androidx.paging.PagingData
import com.example.newsreaderapp.domain.model.Article
import com.example.newsreaderapp.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<PagingData<Article>> {
        return newsRepository.getTopHeadLines().flowOn(Dispatchers.IO)
    }
//    operator fun invoke(scope:CoroutineScope): Flow<Resource<PagingData<Article>>> {
//        val result = newsRepository.getTopHeadLines().flowOn(Dispatchers.IO).cachedIn(scope)
//        return result
//            .onStart { Resource.Loading<PagingData<Article>>() }
//            .map {
//                Resource.Success(it)
//            }.catch { e ->
//                Resource.Error<PagingData<Article>>(
//                    e.message ?: "An Unexpected error occured"
//                )
//            }
//    }


}