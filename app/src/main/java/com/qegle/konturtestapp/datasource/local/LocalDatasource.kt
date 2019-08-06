package com.qegle.konturtestapp.datasource.local

import com.qegle.konturtestapp.datasource.IDatasource
import com.qegle.konturtestapp.datasource.local.database.AppDatabase
import com.qegle.konturtestapp.datasource.model.ContactDS
import io.reactivex.Single

class LocalDatasource(private val database: AppDatabase) : IDatasource {
	
	override fun get(name: String): Single<List<ContactDS>> {
		return database.contactsDao().all
	}
	
	override fun set(list: List<ContactDS>) {
		database.contactsDao().deleteAndCreate(list)
	}
}