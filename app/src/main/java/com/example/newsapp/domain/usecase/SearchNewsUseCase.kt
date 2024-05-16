package com.example.newsapp.domain.usecase

import androidx.paging.PagingData
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(
        searchQuery: String,
        sources: List<String>
    ): Flow<PagingData<Article>> {
        return newsRepository.searchNewsByQuery(
            searchQuery = searchQuery,
            sources = sources
        )
    }
}