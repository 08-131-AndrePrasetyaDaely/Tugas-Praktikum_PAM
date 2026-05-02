package com.example.newsreader.data.repository

import com.example.newsreader.data.model.Article
import com.example.newsreader.data.remote.NewsApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepository(private val apiService: NewsApiService) {
    fun getNews(apiKey: String): Flow<Result<List<Article>>> = flow {
        try {
            val response = apiService.getTopHeadlines("us", apiKey)
            if (response.status == "ok") {
                emit(Result.success(response.articles))
            } else {
                val errorMsg = response.message ?: "Error: ${response.status ?: "Unknown response"}"
                emit(Result.failure(Exception(errorMsg)))
            }
        } catch (e: Exception) {
            emit(Result.failure(Exception("Network Error: ${e.message}")))
        }
    }
}
