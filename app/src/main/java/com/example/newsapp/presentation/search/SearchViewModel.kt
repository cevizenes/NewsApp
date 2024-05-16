package com.example.newsapp.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.usecase.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase
) : ViewModel() {

    private var _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState
    fun onSearchEvent(searchEvent: SearchEvent) {
        when (searchEvent) {
            is SearchEvent.UpdateSearchQuery -> {
                _searchState.value = _searchState.value.copy(searchQuery = searchEvent.searchQuery)
            }

            is SearchEvent.SearchNewsByQuery -> {
                searchNewsByQuery()
            }
        }
    }

    private fun searchNewsByQuery() {
        val articles = searchNewsUseCase(
            searchQuery = _searchState.value.searchQuery,
            sources = listOf("abc-news", "bbc-news")
        ).cachedIn(viewModelScope)
        _searchState.value = _searchState.value.copy(news = articles)
    }

    sealed interface SearchEvent {
        data class UpdateSearchQuery(val searchQuery: String) : SearchEvent
        data object SearchNewsByQuery : SearchEvent
    }

    data class SearchState(
        val searchQuery: String = "",
        val news: Flow<PagingData<Article>>? = null
    )
}
