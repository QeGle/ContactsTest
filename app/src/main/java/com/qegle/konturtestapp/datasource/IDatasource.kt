package com.qegle.konturtestapp.datasource

import com.qegle.konturtestapp.datasource.model.ContactDS
import io.reactivex.Single

interface IDatasource {
	
	fun get(name: String = ""): Single<List<ContactDS>>
	
	fun set(list: List<ContactDS>)
}