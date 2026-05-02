package com.example.newsreader.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsreader.data.model.Article
import com.example.newsreader.data.model.Source
import com.example.newsreader.data.repository.NewsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class NewsUiState {
    data object Loading : NewsUiState()
    data class Success(val articles: List<Article>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading
            delay(1000)

            val mockArticles = listOf(
                Article(
                    title = "Bayern Munich Vs Heidenheim Tuntas 3-3",
                    description = "Pertandingan sengit antara Bayern Munich melawan Heidenheim berakhir dengan skor imbang 3-3 di Bundesliga. Heidenheim memberikan kejutan besar.",
                    urlToImage = "bayern33.jpeg",
                    source = Source(name = "detiksport"),
                    publishedAt = "2026-04-18T10:00:00Z",
                    content = "Pertandingan berlangsung sangat ketat di markas Heidenheim..."
                ),
                Article(
                    title = "Gol bunuh diri yang konyol dari mantan pemain Ajax yang terkenal pada menit k...",
                    description = "Gol bunuh diri yang konyol dari mantan pemain Ajax yang terkenal pada menit ke-80 mengubah jalannya pertandingan. Hal ini menjadi viral di media sosial.",
                    urlToImage = "bayern2.jpeg",
                    source = Source(name = "Goal.com"),
                    publishedAt = "2026-04-18T11:00:00Z",
                    content = "Kekeliruan komunikasi di lini pertahanan mengakibatkan gol bunuh diri..."
                ),
                Article(
                    title = "Ketidakpastian Masa Depan Michael Olise di Bayern Munich Mengaburkan...",
                    description = "Ketidakpastian Masa Depan Michael Olise di Bayern Munich Mengaburkan strategi transfer klub untuk musim depan. Spekulasi kepindahannya semakin menguat.",
                    urlToImage = "bawah.jpeg",
                    source = Source(name = "Jawa Pos"),
                    publishedAt = "2026-04-18T12:00:00Z",
                    content = "Michael Olise yang baru saja bergabung kini dirumorkan tidak nyaman..."
                )
            )

            _uiState.value = NewsUiState.Success(mockArticles)
            _isRefreshing.value = false
        }
    }

    fun refresh() {
        _isRefreshing.value = true
        fetchNews()
    }
}
