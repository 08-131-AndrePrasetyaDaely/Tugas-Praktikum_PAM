package com.example.myprofileapp.platform

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.BatteryManager
import android.os.Build
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart

class AndroidDeviceInfo : DeviceInfo {
    override val model: String = Build.MODEL
    override val manufacturer: String = Build.MANUFACTURER
    override val osVersion: String = Build.VERSION.RELEASE
    override val sdkInt: Int = Build.VERSION.SDK_INT
}

class AndroidNetworkMonitor(private val context: Context) : NetworkMonitor {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override val isOnline: Flow<Boolean> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true)
            }

            override fun onLost(network: Network) {
                trySend(false)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        
        connectivityManager.registerNetworkCallback(request, callback)

        trySend(context.isCurrentlyConnected())

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
    .distinctUntilChanged()

    private fun Context.isCurrentlyConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = cm.activeNetwork ?: return false
        val actNw = cm.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}

class AndroidBatteryInfo(private val context: Context) : BatteryInfo {
    private val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

    override val level: Int
        get() = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

    override val isCharging: Boolean
        get() {
            val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
            return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
        }
}
