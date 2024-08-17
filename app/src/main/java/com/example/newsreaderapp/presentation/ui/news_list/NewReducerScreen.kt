package com.example.newsreaderapp.presentation.ui.news_list

import androidx.compose.runtime.Immutable
import com.example.newsreaderapp.data.sources.remote.model.ArticleDto
import com.example.newsreaderapp.presentation.ui.base.Reducer

class NewReducerScreen :
    Reducer<NewReducerScreen.NewsListState, NewReducerScreen.NewsListEvent, NewReducerScreen.NewsListEffect> {

    @Immutable
    sealed class NewsListEvent : Reducer.ViewEvent {
        data class UpdateNewsLoading(val isLoading: Boolean) : NewsListEvent()
        data class UpdateNews(val news: List<ArticleDto>) : NewsListEvent()
        data class UpdateNewsError(val error: String) : NewsListEvent()

    }

    @Immutable
    sealed class NewsListEffect : Reducer.ViewEffect {
        data class NavigateToNewsDetails(val news: ArticleDto) : NewsListEffect()
    }

    data class NewsListState(
        val isLoading: Boolean,
        val news: List<ArticleDto>,
        val error:String? = null
    ) : Reducer.ViewState

    override fun reduce(
        previousState: NewsListState,
        event: NewsListEvent
    ): Pair<NewsListState, NewsListEffect?> {
        return when (event){
            is NewsListEvent.UpdateNewsLoading -> {
                previousState.copy(isLoading =  event.isLoading) to null
            }
            is NewsListEvent.UpdateNews -> {
                previousState.copy(news = event.news) to null
            }
            is NewsListEvent.UpdateNewsError -> {
                previousState.copy(error = event.error) to null
            }
        }
    }

}