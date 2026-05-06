package com.example.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprofileapp.data.repository.NoteRepository
import com.example.myprofileapp.data.settings.SettingsManager
import com.example.myprofileapp.db.NoteEntity
import com.example.myprofileapp.platform.NetworkMonitor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface NotesUiState {
    object Loading : NotesUiState
    data class Success(val notes: List<NoteEntity>) : NotesUiState
    data class Empty(val message: String) : NotesUiState
}

class NotesViewModel(
    private val repository: NoteRepository,
    private val settingsManager: SettingsManager,
    networkMonitor: NetworkMonitor
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val isOnline: StateFlow<Boolean> = networkMonitor.isOnline
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<NotesUiState> = combine(
        _searchQuery,
        settingsManager.sortOrder
    ) { query, sortOrder ->
        query to sortOrder
    }.flatMapLatest { (query, sortOrder) ->
        val flow = if (query.isEmpty()) {
            repository.getAllNotes()
        } else {
            repository.searchNotes(query)
        }
        
        flow.map { notes ->
            when (sortOrder) {
                "Alphabetical" -> notes.sortedBy { it.title.lowercase() }
                "Oldest" -> notes.sortedBy { it.createdAt }
                else -> notes.sortedByDescending { it.createdAt }
            }
        }
    }.map { notes ->
        if (notes.isEmpty()) {
            NotesUiState.Empty("Catatan tidak ditemukan")
        } else {
            NotesUiState.Success(notes)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NotesUiState.Loading
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insertNote(title, content, false)
        }
    }

    fun updateNote(id: Long, title: String, content: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateNote(id, title, content, isFavorite)
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }

    fun toggleFavorite(note: NoteEntity) {
        viewModelScope.launch {
            repository.updateNote(note.id, note.title, note.content, !note.isFavorite)
        }
    }
}
