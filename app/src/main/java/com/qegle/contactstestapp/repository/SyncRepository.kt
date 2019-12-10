package com.qegle.contactstestapp.repository

import android.content.SharedPreferences
import androidx.core.content.edit

class SyncRepository constructor(
    private val preferences: SharedPreferences
) {
    fun isContactsSyncRequired(): Boolean {
        return isEntitySyncRequired(SYNC_CONTACTS)
    }

    fun isContactsSyncExpired(cachePeriod: Long): Boolean {
        return isEntitySyncExpired(SYNC_CONTACTS, cachePeriod)
    }

    fun updateContactsSync() {
        return updateEntitySync(SYNC_CONTACTS)
    }

    fun resetContactsSync() {
        preferences.edit { putLong(SYNC_CONTACTS, 0) }
    }

    private fun updateEntitySync(key: String) {
        val currentTime = System.currentTimeMillis()
        preferences.edit { putLong(key, currentTime) }
    }

    private fun isEntitySyncRequired(key: String): Boolean {
        return preferences.getLong(key, 0) <= 0
    }

    private fun isEntitySyncExpired(key: String, cachePeriod: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return currentTime - preferences.getLong(key, 0) > cachePeriod
    }

    companion object {
        private const val SYNC_CONTACTS = "sync_contacts"
    }
}