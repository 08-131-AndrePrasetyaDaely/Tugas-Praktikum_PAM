package com.example.myprofileapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Notes : Screen("notes", "Notes", Icons.Default.List)
    object Favorites : Screen("favorites", "Favorites", Icons.Default.Favorite)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    object NoteDetail : Screen("note_detail/{noteId}", "Note Detail") {
        fun createRoute(noteId: Int) = "note_detail/$noteId"
    }
    object AddEditNote : Screen("add_edit_note?noteId={noteId}", "Add/Edit Note") {
        fun createRoute(noteId: Int? = null) = if (noteId != null) "add_edit_note?noteId=$noteId" else "add_edit_note"
    }
}
