package com.example.newsapp.data.remote

import com.example.newsapp.data.remote.dto.NewsResponse
import com.example.newsapp.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    suspend fun getNews(
        @Query("page") pageNumber: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>


    @GET("everything")
    suspend fun searchNewsByQuery(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>


}