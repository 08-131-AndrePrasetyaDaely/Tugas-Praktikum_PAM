package com.example.myprofileapp.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.myprofileapp.data.repository.NoteRepository
import com.example.myprofileapp.data.settings.SettingsManager
import com.example.myprofileapp.db.NotesDatabase
import com.example.myprofileapp.platform.*
import com.example.myprofileapp.viewmodel.NotesViewModel
import com.example.myprofileapp.viewmodel.ProfileViewModel
import com.example.myprofileapp.viewmodel.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Database
    single {
        val driver = AndroidSqliteDriver(NotesDatabase.Schema, androidContext(), "notes.db")
        NotesDatabase(driver)
    }
    
    // DAO / Queries
    single { get<NotesDatabase>().noteEntityQueries }
    
    // Repository
    single { NoteRepository(get()) }
    
    // Settings
    single { SettingsManager(androidContext()) }
    
    // Platform features (expect/actual pattern)
    single<DeviceInfo> { AndroidDeviceInfo() }
    single<NetworkMonitor> { AndroidNetworkMonitor(androidContext()) }
    single<BatteryInfo> { AndroidBatteryInfo(androidContext()) }
    
    // ViewModels
    viewModel { NotesViewModel(get(), get(), get()) }
    viewModel { SettingsViewModel(get(), get(), get()) }
    viewModel { ProfileViewModel() }
}
