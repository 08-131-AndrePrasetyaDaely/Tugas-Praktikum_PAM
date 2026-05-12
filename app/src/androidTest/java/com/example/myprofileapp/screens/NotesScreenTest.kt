package com.example.myprofileapp.screens

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.myprofileapp.db.NoteEntity
import com.example.myprofileapp.viewmodel.NotesUiState
import com.example.myprofileapp.viewmodel.NotesViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class NotesScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel: NotesViewModel = mockk(relaxed = true)
    private val uiState = MutableStateFlow<NotesUiState>(NotesUiState.Loading)
    private val searchQuery = MutableStateFlow("")
    private val isOnline = MutableStateFlow(true)

    init {
        every { viewModel.uiState } returns uiState
        every { viewModel.searchQuery } returns searchQuery
        every { viewModel.isOnline } returns isOnline
    }

    @Test
    fun loadingIndicator_isDisplayed_whenStateIsLoading() {
        uiState.value = NotesUiState.Loading

        composeTestRule.setContent {
            NotesScreen(viewModel = viewModel, onNoteClick = {}, onAddNoteClick = {})
        }

        composeTestRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertIsDisplayed()
    }

    @Test
    fun emptyMessage_isDisplayed_whenStateIsEmpty() {
        val message = "No notes found"
        uiState.value = NotesUiState.Empty(message)

        composeTestRule.setContent {
            NotesScreen(viewModel = viewModel, onNoteClick = {}, onAddNoteClick = {})
        }

        composeTestRule.onNodeWithText(message).assertIsDisplayed()
    }

    @Test
    fun noteList_isDisplayed_whenStateIsSuccess() {
        val notes = listOf(
            NoteEntity(1, "Note 1", "Content 1", false, 1000L),
            NoteEntity(2, "Note 2", "Content 2", true, 2000L)
        )
        uiState.value = NotesUiState.Success(notes)

        composeTestRule.setContent {
            NotesScreen(viewModel = viewModel, onNoteClick = {}, onAddNoteClick = {})
        }

        composeTestRule.onNodeWithText("Note 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Note 2").assertIsDisplayed()
    }

    @Test
    fun offlineIndicator_isDisplayed_whenIsOnlineIsFalse() {
        isOnline.value = false
        uiState.value = NotesUiState.Empty("Empty")

        composeTestRule.setContent {
            NotesScreen(viewModel = viewModel, onNoteClick = {}, onAddNoteClick = {})
        }

        composeTestRule.onNodeWithText("Mode Offline - Tidak ada koneksi internet").assertIsDisplayed()
    }
}
