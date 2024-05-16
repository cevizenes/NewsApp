package com.example.newsapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.usecase.DeleteNewsUseCase
import com.example.newsapp.domain.usecase.GetFavoriteNewUseCase
import com.example.newsapp.domain.usecase.InsertNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val getFavoriteNewUseCase: GetFavoriteNewUseCase,
    private val deleteNewsUseCase: DeleteNewsUseCase,
    private val insertNewsUseCase: InsertNewsUseCase,

    ) : ViewModel() {

    fun onNewsDetailEvent(detailEvent: NewsDetailEvent) {
        when (detailEvent) {
            is NewsDetailEvent.InsertDeleteNews -> {
                viewModelScope.launch {
                    val article = getFavoriteNewUseCase(url = detailEvent.article.url)
                    if(article == null) {
                        insertNew(new = detailEvent.article)
                    }else {
                        deleteNew(new = detailEvent.article)
                    }
                }
            }
        }
    }

    private suspend fun insertNew(new: Article) {
        insertNewsUseCase(article = new)
    }

    private suspend fun deleteNew(new: Article) {
        deleteNewsUseCase(article = new)
    }
}

sealed interface NewsDetailEvent {
    data class InsertDeleteNews(val article: Article) : NewsDetailEvent

}