package com.example.newsreaderapp.domain.useCase

import com.example.newsreaderapp.common.Resource
import com.example.newsreaderapp.data.NewsRepositoryImplementation
import com.example.newsreaderapp.data.model.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNewsUseCase  @Inject constructor(
    private val newsRepository: NewsRepositoryImplementation )  {
    operator fun invoke() : Flow<Resource<NewsResponse>> = flow {
        try {
            emit(Resource.Loading())
            val newsResponse = newsRepository.getTopHeadLines()
            emit(Resource.Success(newsResponse.data))
        } catch (e:Exception){
            emit(Resource.Error(e.message ?: "An Unexpected error occured"))
        }
    }
}