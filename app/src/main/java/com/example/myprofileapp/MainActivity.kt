package com.example.myprofileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myprofileapp.navigation.MainNavigation
import com.example.myprofileapp.ui.theme.MyProfileAppTheme
import com.example.myprofileapp.viewmodel.NotesViewModel
import com.example.myprofileapp.viewmodel.ProfileViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val profileViewModel: ProfileViewModel = viewModel()
            val notesViewModel: NotesViewModel = viewModel()
            val profileUiState by profileViewModel.uiState.collectAsState()

            MyProfileAppTheme(darkTheme = profileUiState.isDarkMode) {
                MainNavigation(
                    notesViewModel = notesViewModel,
                    profileViewModel = profileViewModel
                )
            }
        }
    }
}
