package com.example.newsapp.presentation.favorite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.usecase.GetFavoriteNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteNewsUseCase: GetFavoriteNewsUseCase
) : ViewModel() {
    private val _favoriteState = mutableStateOf(FavoriteState())
    val favoriteState: State<FavoriteState> = _favoriteState

    init {
        getNews()
    }

    private fun getNews() {
        getFavoriteNewsUseCase().onEach {
            _favoriteState.value = _favoriteState.value.copy(news = it)
        }.launchIn(viewModelScope)
    }
}


data class FavoriteState(
    val news: List<Article> = emptyList()
)