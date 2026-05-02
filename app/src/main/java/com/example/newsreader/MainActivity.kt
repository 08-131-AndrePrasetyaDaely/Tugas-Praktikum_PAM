package com.example.newsreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsreader.data.model.Article
import com.example.newsreader.data.remote.NewsApiService
import com.example.newsreader.data.repository.NewsRepository
import com.example.newsreader.ui.screen.ArticleDetailScreen
import com.example.newsreader.ui.screen.NewsListScreen
import com.example.newsreader.ui.theme.NewsReaderTheme
import com.example.newsreader.ui.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val apiService = NewsApiService()
        val repository = NewsRepository(apiService)
        val viewModel = NewsViewModel(repository)

        setContent {
            NewsReaderTheme {
                NewsApp(viewModel)
            }
        }
    }
}

@Composable
fun NewsApp(viewModel: NewsViewModel) {
    val navController = rememberNavController()
    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            NewsListScreen(
                viewModel = viewModel,
                onArticleClick = { article ->
                    selectedArticle = article
                    navController.navigate("detail")
                }
            )
        }
        composable("detail") {
            selectedArticle?.let { article ->
                ArticleDetailScreen(
                    article = article,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
