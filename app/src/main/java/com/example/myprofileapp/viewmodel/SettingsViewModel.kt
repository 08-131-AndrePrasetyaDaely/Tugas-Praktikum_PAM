package com.example.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprofileapp.data.settings.SettingsManager
import com.example.myprofileapp.platform.BatteryInfo
import com.example.myprofileapp.platform.DeviceInfo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsManager: SettingsManager,
    val deviceInfo: DeviceInfo,
    val batteryInfo: BatteryInfo
) : ViewModel() {
    val isDarkMode: StateFlow<Boolean> = settingsManager.isDarkMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val sortOrder: StateFlow<String> = settingsManager.sortOrder
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Newest")

    fun toggleDarkMode(isDark: Boolean) {
        viewModelScope.launch {
            settingsManager.setDarkMode(isDark)
        }
    }

    fun setSortOrder(order: String) {
        viewModelScope.launch {
            settingsManager.setSortOrder(order)
        }
    }
}
