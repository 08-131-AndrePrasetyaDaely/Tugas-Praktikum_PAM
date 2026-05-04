package com.example.myprofileapp.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.myprofileapp.db.NoteEntity
import com.example.myprofileapp.db.NoteEntityQueries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class NoteRepository(
    private val queries: NoteEntityQueries,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getAllNotes(): Flow<List<NoteEntity>> {
        return queries.getAllNotes().asFlow().mapToList(ioDispatcher)
    }

    fun searchNotes(query: String): Flow<List<NoteEntity>> {
        return queries.searchNotes(query).asFlow().mapToList(ioDispatcher)
    }

    suspend fun insertNote(title: String, content: String, isFavorite: Boolean) {
        queries.insertNote(title, content, isFavorite, System.currentTimeMillis())
    }

    suspend fun updateNote(id: Long, title: String, content: String, isFavorite: Boolean) {
        queries.updateNote(title, content, isFavorite, id)
    }

    suspend fun deleteNote(id: Long) {
        queries.deleteNote(id)
    }
}
