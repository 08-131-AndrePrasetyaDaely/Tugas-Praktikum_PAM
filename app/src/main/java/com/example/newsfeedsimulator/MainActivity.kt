package com.example.newsfeedsimulator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsfeedsimulator.model.NewsDisplayModel
import com.example.newsfeedsimulator.ui.theme.NewsFeedSimulatorTheme
import com.example.newsfeedsimulator.viewmodel.NewsViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsFeedSimulatorTheme {
                NewsFeedScreen()
            }
        }
    }
}

@Composable
fun NewsFeedScreen(viewModel: NewsViewModel = viewModel()) {
    val readCount by viewModel.readCount.collectAsState()
    val newsList = remember { mutableStateListOf<NewsDisplayModel>() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Collect news flow
    LaunchedEffect(Unit) {
        viewModel.newsDisplayFlow.collect { news ->
            newsList.add(0, news) // Tambahkan ke paling atas
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "News Feed Simulator",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Berita dibaca: $readCount",
                style = MaterialTheme.typography.bodyLarge
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row {
                Button(onClick = { viewModel.selectedCategory.value = "All" }) {
                    Text("All")
                }
                Spacer(modifier = Modifier.width(4.dp))
                Button(onClick = { 
                    viewModel.selectedCategory.value = "Technology" 
                    newsList.clear() // Bersihkan list saat ganti filter
                }) {
                    Text("Tech")
                }
                Spacer(modifier = Modifier.width(4.dp))
                Button(onClick = { 
                    viewModel.selectedCategory.value = "Sports" 
                    newsList.clear()
                }) {
                    Text("Sports")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(newsList) { news ->
                    NewsItemCard(news) {
                        viewModel.incrementReadCount()
                        // Simulasi fetch detail secara async
                        scope.launch {
                            val detail = viewModel.fetchNewsDetailAsync(news.hashCode())
                            Toast.makeText(context, detail, Toast.LENGTH_SHORT).show()
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun NewsItemCard(news: NewsDisplayModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Text(text = news.category, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = news.timeFormatted, style = MaterialTheme.typography.labelSmall)
            }
            Text(text = news.title, style = MaterialTheme.typography.titleLarge)
            Button(onClick = onClick, modifier = Modifier.padding(top = 8.dp)) {
                Text("Baca Selengkapnya")
            }
        }
    }
}
