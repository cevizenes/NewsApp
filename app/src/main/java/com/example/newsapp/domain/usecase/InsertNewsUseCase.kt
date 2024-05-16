package com.example.newsapp.domain.usecase

import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.domain.model.Article
import javax.inject.Inject

class InsertNewsUseCase @Inject constructor(
    private val newsDao: NewsDao
) {
    suspend operator fun invoke(article: Article) {
        newsDao.insert(article = article)
    }
}