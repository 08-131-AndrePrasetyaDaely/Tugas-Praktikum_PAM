package com.example.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myprofileapp.data.settings.SettingsManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsManager: SettingsManager) : ViewModel() {
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

    class Factory(private val settingsManager: SettingsManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SettingsViewModel(settingsManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
