package com.example.myprofileapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myprofileapp.viewmodel.NotesUiState
import com.example.myprofileapp.viewmodel.NotesViewModel

@Composable
fun FavoritesScreen(
    viewModel: NotesViewModel,
    onNoteClick: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is NotesUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is NotesUiState.Success -> {
            val favoriteNotes = state.notes.filter { it.isFavorite }
            if (favoriteNotes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Belum ada catatan favorit")
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(favoriteNotes, key = { it.id }) { note ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { onNoteClick(note.id) }
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                                    Text(text = note.content, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
                                }
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Favorit",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Favorit tidak ditemukan")
            }
        }
    }
}
