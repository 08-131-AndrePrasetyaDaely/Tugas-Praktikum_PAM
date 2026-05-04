package com.example.myprofileapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myprofileapp.db.NoteEntity
import com.example.myprofileapp.viewmodel.NotesUiState
import com.example.myprofileapp.viewmodel.NotesViewModel

@Composable
fun NotesScreen(
    viewModel: NotesViewModel,
    onNoteClick: (Long) -> Unit,
    onAddNoteClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNoteClick) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Catatan")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Cari Catatan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )

            when (val state = uiState) {
                is NotesUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is NotesUiState.Empty -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(state.message)
                    }
                }
                is NotesUiState.Success -> {
                    NoteList(
                        notes = state.notes,
                        onNoteClick = onNoteClick,
                        onFavoriteToggle = { viewModel.toggleFavorite(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun NoteList(
    notes: List<NoteEntity>,
    onNoteClick: (Long) -> Unit,
    onFavoriteToggle: (NoteEntity) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(notes, key = { it.id }) { note ->
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
                    IconButton(onClick = { onFavoriteToggle(note) }) {
                        Icon(
                            imageVector = if (note.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (note.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
