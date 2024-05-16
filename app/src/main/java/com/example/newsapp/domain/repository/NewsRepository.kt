package com.example.newsapp.domain.repository

import androidx.paging.PagingData
import com.example.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>

    fun searchNewsByQuery(searchQuery: String, sources: List<String>): Flow<PagingData<Article>>

    suspend fun insert(article: Article)

    suspend fun delete(article: Article)

    fun getArticles(): Flow<List<Article>>

    suspend fun getArticle(url: String): Article?
}