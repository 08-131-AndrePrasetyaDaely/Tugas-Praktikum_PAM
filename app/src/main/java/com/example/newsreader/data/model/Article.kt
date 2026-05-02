package com.example.newsreader.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<Article> = emptyList(),
    val message: String? = null
)

@Serializable
data class Article(
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null,
    val source: Source? = null
)

@Serializable
data class Source(
    val id: String? = null,
    val name: String? = null
)
