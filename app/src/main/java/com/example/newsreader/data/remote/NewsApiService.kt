package com.example.newsreader.data.remote

import com.example.newsreader.data.model.NewsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class NewsApiService {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
    }

    suspend fun getTopHeadlines(country: String, apiKey: String): NewsResponse {
        return client.get("https://newsapi.org/v2/top-headlines") {
            parameter("country", country)
            parameter("apiKey", apiKey)
        }.body()
    }
}
