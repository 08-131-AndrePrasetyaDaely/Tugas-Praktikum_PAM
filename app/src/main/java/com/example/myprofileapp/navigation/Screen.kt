package com.example.myprofileapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Notes : Screen("notes", "Catatan", Icons.Default.List)
    object Favorites : Screen("favorites", "Favorit", Icons.Default.Favorite)
    object AiChat : Screen("ai_chat", "Asisten AI", Icons.Default.AutoAwesome)
    object Profile : Screen("profile", "Profil", Icons.Default.Person)
    object Settings : Screen("settings", "Pengaturan", Icons.Default.Settings)
    
    object NoteDetail : Screen("note_detail/{noteId}", "Detail Catatan") {
        fun createRoute(noteId: Long) = "note_detail/$noteId"
    }
    
    object AddEditNote : Screen("add_edit_note?noteId={noteId}", "Tambah/Edit Catatan") {
        fun createRoute(noteId: Long? = null) = if (noteId != null) "add_edit_note?noteId=$noteId" else "add_edit_note"
    }
}
