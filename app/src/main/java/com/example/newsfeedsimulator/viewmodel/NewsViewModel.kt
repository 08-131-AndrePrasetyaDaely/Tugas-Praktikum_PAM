package com.example.newsfeedsimulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeedsimulator.model.NewsDisplayModel
import com.example.newsfeedsimulator.model.NewsItem
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewsViewModel : ViewModel() {

    private val _readCount = MutableStateFlow(0)
    val readCount: StateFlow<Int> = _readCount.asStateFlow()

    private val categories = listOf("Politics", "Technology", "Sports", "Entertainment")
    
    // 1. Flow yang mensimulasikan data berita baru setiap 2 detik
    val rawNewsFlow: Flow<NewsItem> = flow {
        var id = 1
        while (true) {
            val news = NewsItem(
                id = id++,
                title = "Berita #$id",
                category = categories.random(),
                content = "Konten detail untuk berita ke-$id yang sangat menarik.",
                timestamp = System.currentTimeMillis()
            )
            emit(news)
            delay(2000)
        }
    }.catch { e ->
        // Bonus: Implementasi error handling dengan .catch
        println("Error in news flow: ${e.message}")
    }

    // State untuk filter
    val selectedCategory = MutableStateFlow("All")

    // 2. Filter berita berdasarkan kategori tertentu
    // 3. Transform data menjadi format yang ditampilkan
    val newsDisplayFlow: Flow<NewsDisplayModel> = rawNewsFlow
        .filter { item ->
            selectedCategory.value == "All" || item.category == selectedCategory.value
        }
        .map { item ->
            // Transformasi format
            NewsDisplayModel(
                title = item.title,
                category = "[${item.category}]",
                timeFormatted = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(item.timestamp))
            )
        }
        .onEach {
            // Kita bisa melakukan sesuatu setiap ada berita baru yang lolos filter
        }

    fun incrementReadCount() {
        // 4. StateFlow untuk menyimpan jumlah berita yang sudah dibaca
        _readCount.value += 1
    }

    // 5. Coroutines untuk mengambil detail berita secara async
    suspend fun fetchNewsDetailAsync(id: Int): String {
        val deferredDetail: Deferred<String> = viewModelScope.async(Dispatchers.IO) {
            delay(1000) // Simulasi network call
            "Detail berita untuk ID $id berhasil diambil secara asynchronous."
        }
        return deferredDetail.await()
    }
}
