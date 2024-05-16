package com.example.newsapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.domain.model.Article

class SearchNewsPagingSource(
    private val api: ApiService,
    private val searchQuery: String,
    private val sources: String
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val pageNumber = params.key ?: 1
            val response = api.searchNewsByQuery(searchQuery, pageNumber, sources)
            if (response.isSuccessful) {
                val articles = response.body()?.articles?.distinctBy { it.title } ?: emptyList()

                LoadResult.Page(
                    data = articles,
                    prevKey = if (pageNumber == 1) null else pageNumber - 1,
                    nextKey = pageNumber.plus(1)
                )
            } else {
                return LoadResult.Error(Exception("Failed"))
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}