package com.example.newsreaderapp.presentation.ui.news_list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsreaderapp.domain.model.Article
import com.example.newsreaderapp.domain.useCase.GetNewsUseCase
import com.example.newsreaderapp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(val getNewsUseCase: GetNewsUseCase) :
    BaseViewModel<NewReducerScreen.NewsListState, NewReducerScreen.NewsListEvent, NewReducerScreen.NewsListEffect>(
        initialState = NewReducerScreen.NewsListState(isLoading = true, news = emptyList()),
        reducer = NewReducerScreen()
    ) {
    val newsPagingDataFlow: Flow<PagingData<Article>> = getNewsUseCase()
        .cachedIn(viewModelScope)
    private val _selectedCategory = MutableStateFlow<String>("")
    val selectedCategory: StateFlow<String> = _selectedCategory

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
    fun onOptionSelected(option: String) {
        _selectedCategory.value = option
        //   makeApiRequest(option)
    }
}