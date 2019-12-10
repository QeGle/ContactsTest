package com.qegle.contactstestapp.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.qegle.contactstestapp.entity.Contact
import io.reactivex.Single


@Dao
interface ContactsDao {
	
	@get:Query("SELECT * FROM contact")
	val all: Single<List<Contact>>
	
	@Insert(onConflict = REPLACE)
	fun insert(contactDS: Contact)
	
	@Delete
	fun delete(contactDS: Contact)
	
	@Transaction
	fun deleteAndCreate(users: List<Contact>) {
		deleteAll()
		insertAll(users)
	}
	
	@Query("DELETE FROM contact")
	fun deleteAll()
	
	@Insert
	fun insertAll(users: List<Contact>)
}