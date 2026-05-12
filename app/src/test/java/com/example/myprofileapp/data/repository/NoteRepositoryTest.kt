package com.example.myprofileapp.data.repository

import com.example.myprofileapp.db.NoteEntityQueries
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NoteRepositoryTest {
    private lateinit var repository: NoteRepository
    private val queries: NoteEntityQueries = mockk(relaxed = true)

    @Before
    fun setup() {
        repository = NoteRepository(queries)
    }

    @Test
    fun `insertNote calls queries insertNote`() = runTest {
        val title = "Test Title"
        val content = "Test Content"
        val isFavorite = false

        repository.insertNote(title, content, isFavorite)

        verify { queries.insertNote(title, content, isFavorite, any()) }
    }

    @Test
    fun `updateNote calls queries updateNote`() = runTest {
        val id = 1L
        val title = "Updated Title"
        val content = "Updated Content"
        val isFavorite = true

        repository.updateNote(id, title, content, isFavorite)

        verify { queries.updateNote(title, content, isFavorite, id) }
    }

    @Test
    fun `deleteNote calls queries deleteNote`() = runTest {
        val id = 1L

        repository.deleteNote(id)

        verify { queries.deleteNote(id) }
    }

    @Test
    fun `getAllNotes calls queries getAllNotes`() = runTest {
        repository.getAllNotes()
        verify { queries.getAllNotes() }
    }

    @Test
    fun `searchNotes calls queries searchNotes`() = runTest {
        val query = "search"
        repository.searchNotes(query)
        verify { queries.searchNotes(query) }
    }
}
