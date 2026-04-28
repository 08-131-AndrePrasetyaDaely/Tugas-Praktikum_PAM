package com.example.newsfeedsimulator.model

data class NewsItem(
    val id: Int,
    val title: String,
    val category: String,
    val content: String,
    val timestamp: Long
)

data class NewsDisplayModel(
    val title: String,
    val category: String,
    val timeFormatted: String
)
