package com.example.newsapp.domain.usecase

import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.domain.model.Article
import javax.inject.Inject

class DeleteNewsUseCase @Inject constructor(
    private val newsDao: NewsDao
){
    suspend operator fun invoke(article: Article) {
        newsDao.delete(article = article)
    }
}