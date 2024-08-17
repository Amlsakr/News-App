package com.example.newsreaderapp.presentation.ui.news_list

import androidx.lifecycle.viewModelScope
import com.example.newsreaderapp.common.Resource
import com.example.newsreaderapp.domain.useCase.GetNewsUseCase
import com.example.newsreaderapp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(val getNewsUseCase: GetNewsUseCase) :
    BaseViewModel<NewReducerScreen.NewsListState, NewReducerScreen.NewsListEvent, NewReducerScreen.NewsListEffect>(
        initialState = NewReducerScreen.NewsListState(isLoading = true, news = emptyList()),
        reducer = NewReducerScreen()
    ) {
    init {
        viewModelScope.launch {
            getNewsUseCase.invoke().collect { result ->
                sendEvent(
                    event = NewReducerScreen.NewsListEvent.UpdateNewsLoading(
                        isLoading = result.isLoading()
                    )
                )
                when (result) {
                    is Resource.Success -> {
                        sendEvent(
                            event = NewReducerScreen.NewsListEvent.UpdateNews(
                                news = result.data?.articles!!
                            )
                        )
                    }

                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                }
            }

        }
    }

}