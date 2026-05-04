package com.example.myprofileapp

import android.app.Application
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.myprofileapp.data.repository.NoteRepository
import com.example.myprofileapp.data.settings.SettingsManager
import com.example.myprofileapp.db.NotesDatabase

class NotesApplication : Application() {
    lateinit var noteRepository: NoteRepository
    lateinit var settingsManager: SettingsManager

    override fun onCreate() {
        super.onCreate()
        
        val driver = AndroidSqliteDriver(NotesDatabase.Schema, this, "notes.db")
        val database = NotesDatabase(driver)
        noteRepository = NoteRepository(database.noteEntityQueries)
        settingsManager = SettingsManager(this)
    }
}
