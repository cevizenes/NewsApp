package com.example.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.domain.model.Article
import com.example.newsapp.data.remote.NewsDataSource
import com.example.newsapp.data.remote.SearchNewsPagingSource
import com.example.newsapp.data.remote.ApiService
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val newsDao: NewsDao
) : NewsRepository {
    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsDataSource(
                    api = api,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override fun searchNewsByQuery(
        searchQuery: String,
        sources: List<String>
    ): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    api = api,
                    searchQuery = searchQuery,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }


    override suspend fun insert(article: Article) {
        newsDao.insert(article)
    }

    override suspend fun delete(article: Article) {
        newsDao.insert(article)
    }

    override fun getArticles(): Flow<List<Article>> {
        return newsDao.getArticles()
    }

    override suspend fun getArticle(url: String): Article? {
        return newsDao.getArticle(url)
    }

}