package com.example.myprofileapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myprofileapp.ui.components.EditProfileForm
import com.example.myprofileapp.ui.components.ProfileCard
import com.example.myprofileapp.ui.components.ProfileHeader
import com.example.myprofileapp.viewmodel.ProfileViewModel
import com.example.myprofileapp.viewmodel.SettingsViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    settingsViewModel: SettingsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val isDarkMode by settingsViewModel.isDarkMode.collectAsState()
    var isEditMode by remember { mutableStateOf(false) }
    var isDetailsVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dark Mode Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Mode Gelap", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = isDarkMode,
                onCheckedChange = { settingsViewModel.toggleDarkMode(it) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isEditMode) {
            EditProfileForm(
                name = uiState.name,
                bio = uiState.bio,
                onNameChange = { viewModel.updateName(it) },
                onBioChange = { viewModel.updateBio(it) },
                onSave = { isEditMode = false }
            )
        } else {
            ProfileHeader(
                name = uiState.name,
                bio = uiState.bio
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { isDetailsVisible = !isDetailsVisible }) {
                    Text(if (isDetailsVisible) "Sembunyikan Detail" else "Tampilkan Detail")
                }
                
                OutlinedButton(onClick = { isEditMode = true }) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Edit Profil")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(
                visible = isDetailsVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                ProfileCard(
                    email = uiState.email,
                    phone = uiState.phone,
                    location = uiState.location
                )
            }
        }
    }
}
