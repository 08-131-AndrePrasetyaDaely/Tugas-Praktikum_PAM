package com.example.newsreader.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.remember
import coil.compose.AsyncImage
import com.example.newsreader.data.model.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    article: Article,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val imageModel = remember(article.urlToImage) {
        if (article.urlToImage?.startsWith("http") == true) {
            article.urlToImage
        } else {
            val resourceName = article.urlToImage?.substringBefore(".") ?: ""
            context.resources.getIdentifier(resourceName, "drawable", context.packageName)
                .takeIf { it != 0 } ?: article.urlToImage
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Detail Berita", 
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A148C),
                        fontSize = 18.sp
                    ) 
                },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text("Kembali", color = Color(0xFF6750A4))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF3E5F5)
                )
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = imageModel,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = article.title ?: "No Title",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                val date = article.publishedAt?.split("T")?.get(0) ?: "Unknown Date"
                Text(
                    text = "Dipublikasikan: $date",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = article.description ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = article.content ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
