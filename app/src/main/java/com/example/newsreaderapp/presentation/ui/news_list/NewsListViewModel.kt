package com.example.newsreaderapp.presentation.ui.news_list

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsreaderapp.common.utill.Resource
import com.example.newsreaderapp.domain.model.Article
import com.example.newsreaderapp.domain.useCase.GetNewsUseCase
import com.example.newsreaderapp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(val getNewsUseCase: GetNewsUseCase) :
    BaseViewModel<NewReducerScreen.NewsListState, NewReducerScreen.NewsListEvent, NewReducerScreen.NewsListEffect>(
        initialState = NewReducerScreen.NewsListState(isLoading = true, news = emptyList()),
        reducer = NewReducerScreen()
    ) {
    val newsPagingDataFlow: Flow<PagingData<Article>> = getNewsUseCase()
        .cachedIn(viewModelScope)
//    init {
//        viewModelScope.launch {
//            getNewsUseCase.invoke().collect { result ->
//                sendEvent(
//                    event = NewReducerScreen.NewsListEvent.UpdateNewsLoading(
//                        isLoading = result.isLoading()
//                    )
//                )
//                when (result) {
//                    is Resource.Success -> {
//                        if (!result.data?.articleDtos.isNullOrEmpty())
//                        sendEvent(
//                            event = NewReducerScreen.NewsListEvent.UpdateNews(
//                                news = result.data?.articleDtos!!
//                            )
//                        )
//                    }
//
//                    is Resource.Error -> {
//                        sendEvent(
//                            event = NewReducerScreen.NewsListEvent.UpdateNewsError(
//                                error = "There is error"
//                            )
//                        )
//                        Log.e("error", "there is unexpected error")
//                    }
//                    is Resource.Loading -> Unit
//                }
//            }
//
//        }
//    }

}