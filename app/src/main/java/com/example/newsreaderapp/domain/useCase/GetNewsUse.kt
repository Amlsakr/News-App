package com.example.newsreaderapp.domain.useCase

import androidx.paging.PagingData
import com.example.newsreaderapp.data.sources.repository.NewsRepositoryImplementation
import com.example.newsreaderapp.domain.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepositoryImplementation
) {
    operator fun invoke(): Flow<PagingData<Article>> {
        return newsRepository.getTopHeadLines().flowOn(Dispatchers.IO)
    }
}