package com.example.myprofileapp.platform

import kotlinx.coroutines.flow.Flow

interface DeviceInfo {
    val model: String
    val manufacturer: String
    val osVersion: String
    val sdkInt: Int
}

interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}

interface BatteryInfo {
    val level: Int
    val isCharging: Boolean
}
