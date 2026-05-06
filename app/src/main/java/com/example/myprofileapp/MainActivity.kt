package com.example.myprofileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.myprofileapp.navigation.MainNavigation
import com.example.myprofileapp.ui.theme.MyProfileAppTheme
import com.example.myprofileapp.viewmodel.ProfileViewModel
import com.example.myprofileapp.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = koinViewModel()
            val profileViewModel: ProfileViewModel = koinViewModel()
            
            val isDarkMode by settingsViewModel.isDarkMode.collectAsState()

            MyProfileAppTheme(darkTheme = isDarkMode) {
                MainNavigation(
                    profileViewModel = profileViewModel
                )
            }
        }
    }
}
