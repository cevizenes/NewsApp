package com.example.newsapp.domain.usecase

import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteNewsUseCase @Inject constructor(
    private val newsDao: NewsDao
) {
    operator fun invoke(): Flow<List<Article>> {
        return newsDao.getArticles()
    }
}