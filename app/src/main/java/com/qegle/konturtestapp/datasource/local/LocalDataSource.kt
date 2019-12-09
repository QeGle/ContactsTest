package com.qegle.konturtestapp.datasource.local

import com.qegle.konturtestapp.datasource.IDataSource
import com.qegle.konturtestapp.datasource.local.database.AppDatabase
import com.qegle.konturtestapp.datasource.model.ContactDS
import io.reactivex.Single

class LocalDataSource(private val database: AppDatabase) : IDataSource {
	
	override fun get(name: String): Single<List<ContactDS>> {
		return database.contactsDao().all
	}
	
	override fun set(list: List<ContactDS>) {
		database.contactsDao().deleteAndCreate(list)
	}
}