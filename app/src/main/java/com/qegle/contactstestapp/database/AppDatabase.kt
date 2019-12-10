package com.qegle.contactstestapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.qegle.contactstestapp.entity.Contact

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactsDao(): ContactsDao
}