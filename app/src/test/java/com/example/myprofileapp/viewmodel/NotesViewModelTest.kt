package com.example.myprofileapp.viewmodel

import app.cash.turbine.test
import com.example.myprofileapp.data.repository.NoteRepository
import com.example.myprofileapp.data.settings.SettingsManager
import com.example.myprofileapp.db.NoteEntity
import com.example.myprofileapp.platform.NetworkMonitor
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {
    private lateinit var viewModel: NotesViewModel
    private val repository: NoteRepository = mockk(relaxed = true)
    private val settingsManager: SettingsManager = mockk(relaxed = true)
    private val networkMonitor: NetworkMonitor = mockk(relaxed = true)
    
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { settingsManager.sortOrder } returns flowOf("Newest")
        every { networkMonitor.isOnline } returns flowOf(true)
        every { repository.getAllNotes() } returns flowOf(emptyList())
        
        viewModel = NotesViewModel(repository, settingsManager, networkMonitor)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state logic`() = runTest {
        viewModel.uiState.test {
            val item = awaitItem()
            assertTrue(item is NotesUiState.Loading || item is NotesUiState.Empty)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState updates to Success when notes are loaded`() = runTest {
        val notes = listOf(
            NoteEntity(1, "Title", "Content", false, 1000L)
        )
        every { repository.getAllNotes() } returns flowOf(notes)
        
        viewModel = NotesViewModel(repository, settingsManager, networkMonitor)
        
        viewModel.uiState.test {
            val item = awaitItem()
            if (item is NotesUiState.Loading) {
                assertEquals(NotesUiState.Success(notes), awaitItem())
            } else {
                assertEquals(NotesUiState.Success(notes), item)
            }
        }
    }

    @Test
    fun `uiState updates to Empty when no notes found`() = runTest {
        every { repository.getAllNotes() } returns flowOf(emptyList())
        
        viewModel = NotesViewModel(repository, settingsManager, networkMonitor)
        
        viewModel.uiState.test {
            val item = awaitItem()
            if (item is NotesUiState.Loading) {
                val next = awaitItem()
                assertTrue(next is NotesUiState.Empty)
            } else {
                assertTrue(item is NotesUiState.Empty)
            }
        }
    }

    @Test
    fun `onSearchQueryChange updates searchQuery flow`() = runTest {
        val query = "search query"
        viewModel.onSearchQueryChange(query)
        assertEquals(query, viewModel.searchQuery.value)
    }

    @Test
    fun `toggleFavorite calls repository updateNote`() = runTest {
        val note = NoteEntity(1, "Title", "Content", false, 1000L)
        viewModel.toggleFavorite(note)
        
        coVerify { repository.updateNote(1, "Title", "Content", true) }
    }

    @Test
    fun `isOnline flow works with Turbine`() = runTest {
        val isOnlineFlow = MutableStateFlow(true)
        every { networkMonitor.isOnline } returns isOnlineFlow
        
        viewModel = NotesViewModel(repository, settingsManager, networkMonitor)
        
        viewModel.isOnline.test {
            assertEquals(true, awaitItem())
            isOnlineFlow.value = false
            assertEquals(false, awaitItem())
        }
    }
}
