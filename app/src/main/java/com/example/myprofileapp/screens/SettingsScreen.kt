package com.example.myprofileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myprofileapp.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val sortOrder by viewModel.sortOrder.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Pengaturan Aplikasi", style = MaterialTheme.typography.headlineMedium)
        
        HorizontalDivider()
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Mode Gelap", style = MaterialTheme.typography.titleLarge)
                Text("Beralih antara tema terang dan gelap", style = MaterialTheme.typography.bodyMedium)
            }
            Switch(
                checked = isDarkMode,
                onCheckedChange = { viewModel.toggleDarkMode(it) }
            )
        }
        
        HorizontalDivider()
        
        Column {
            Text("Informasi Perangkat", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            DeviceInfoItem(label = "Model", value = viewModel.deviceInfo.model)
            DeviceInfoItem(label = "Manufaktur", value = viewModel.deviceInfo.manufacturer)
            DeviceInfoItem(label = "Versi OS", value = "Android ${viewModel.deviceInfo.osVersion}")
            DeviceInfoItem(label = "Baterai", value = "${viewModel.batteryInfo.level}% ${if (viewModel.batteryInfo.isCharging) "(Mengisi daya)" else ""}")
        }
        
        HorizontalDivider()
        
        Column {
            Text("Urutan catatan dalam daftar", style = MaterialTheme.typography.bodyMedium)
            
            Spacer(modifier = Modifier.height(8.dp))
            
            val options = listOf("Terbaru" to "Newest", "Terlama" to "Oldest", "Alfabetis" to "Alphabetical")
            options.forEach { (label, value) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = sortOrder == value,
                        onClick = { viewModel.setSortOrder(value) }
                    )
                    Text(label, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}

@Composable
fun DeviceInfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
    }
}
