package com.example.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myprofileapp.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NotesViewModel : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(
        listOf(
            Note(1, "Belajar Jetpack Compose", "Mempelajari Navigation dan Bottom Bar."),
            Note(2, "Tugas PAM", "Mengerjakan tugas minggu ke-5."),
            Note(3, "Catatan Harian", "Hari ini cuaca sangat cerah.")
        )
    )
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    fun addNote(title: String, content: String) {
        val newNote = Note(
            id = if (_notes.value.isEmpty()) 1 else _notes.value.maxOf { it.id } + 1,
            title = title,
            content = content
        )
        _notes.update { it + newNote }
    }

    fun updateNote(id: Int, title: String, content: String) {
        _notes.update { list ->
            list.map { if (it.id == id) it.copy(title = title, content = content) else it }
        }
    }

    fun deleteNote(id: Int) {
        _notes.update { list -> list.filter { it.id != id } }
    }

    fun toggleFavorite(id: Int) {
        _notes.update { list ->
            list.map { if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it }
        }
    }
}
