package com.qegle.konturtestapp.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

import com.qegle.konturtestapp.datasource.model.ContactDS

@Database(entities = [ContactDS::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
	
	abstract fun contactsDao(): ContactsDao
}