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
import com.example.myprofileapp.viewmodel.ProfileViewModel
import com.example.myprofileapp.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val app = applicationContext as NotesApplication
            
            val settingsViewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModel.Factory(app.settingsManager)
            )
            
            val profileViewModel: ProfileViewModel = viewModel()
            
            val isDarkMode by settingsViewModel.isDarkMode.collectAsState()

            MyProfileAppTheme(darkTheme = isDarkMode) {
                MainNavigation(
                    profileViewModel = profileViewModel
                )
            }
        }
    }
}
