package com.example.myprofileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myprofileapp.viewmodel.NotesUiState
import com.example.myprofileapp.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    noteId: Long? = null,
    viewModel: NotesViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val note = (uiState as? NotesUiState.Success)?.notes?.find { it.id == noteId }

    var title by remember(note) { mutableStateOf(note?.title ?: "") }
    var content by remember(note) { mutableStateOf(note?.content ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == null) "Tambah Catatan" else "Edit Catatan") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (noteId == null) {
                            viewModel.addNote(title, content)
                        } else {
                            viewModel.updateNote(noteId, title, content, note?.isFavorite ?: false)
                        }
                        onBack()
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Simpan")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Judul") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Isi Catatan") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 5
            )
        }
    }
}
