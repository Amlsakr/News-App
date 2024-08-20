package com.example.newsreaderapp.domain.useCase

import com.example.newsreaderapp.common.utill.Resource
import com.example.newsreaderapp.data.sources.remote.model.NewsResponse
import com.example.newsreaderapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNewsByCategory @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(category: String): Flow<Resource<NewsResponse>> = flow {
        try {
            emit(Resource.Loading())
            val newsResponse = newsRepository.getTopHeadLinesByCategory(category)
            emit(Resource.Success(data = newsResponse.data))
        }catch (e:Exception){
            emit(Resource.Error(e.message ?: "An Unexpected error occured"))
        }
    }
}