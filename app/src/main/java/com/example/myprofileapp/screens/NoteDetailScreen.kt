package com.example.myprofileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myprofileapp.viewmodel.NotesUiState
import com.example.myprofileapp.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: Long,
    viewModel: NotesViewModel,
    onBack: () -> Unit,
    onEdit: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val note = (uiState as? NotesUiState.Success)?.notes?.find { it.id == noteId }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hapus Catatan") },
            text = { Text("Apakah Anda yakin ingin menghapus catatan ini?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteNote(noteId)
                    showDeleteDialog = false
                    onBack()
                }) {
                    Text("Hapus", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Catatan") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    if (note != null) {
                        IconButton(onClick = { viewModel.toggleFavorite(note) }) {
                            Icon(
                                imageVector = if (note.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorit"
                            )
                        }
                    }
                    IconButton(onClick = { onEdit(noteId) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Hapus")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (note != null) {
                Text(text = note.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = note.content, style = MaterialTheme.typography.bodyLarge)
            } else {
                Text(text = "Catatan tidak ditemukan")
            }
        }
    }
}
