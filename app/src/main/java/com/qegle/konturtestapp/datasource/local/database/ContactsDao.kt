package com.qegle.konturtestapp.datasource.local.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.qegle.konturtestapp.datasource.model.ContactDS
import io.reactivex.Single


@Dao
interface ContactsDao {
	
	@get:Query("SELECT * FROM contactds")
	val all: Single<List<ContactDS>>
	
	@Insert(onConflict = REPLACE)
	fun insert(contactDS: ContactDS)
	
	@Delete
	fun delete(contactDS: ContactDS)
	
	@Transaction
	fun deleteAndCreate(users: List<ContactDS>) {
		deleteAll()
		insertAll(users)
	}
	
	@Query("DELETE FROM contactds")
	fun deleteAll()
	
	@Insert
	fun insertAll(users: List<ContactDS>)
}