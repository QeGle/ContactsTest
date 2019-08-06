package com.qegle.konturtestapp.repository

import com.qegle.konturtestapp.repository.model.ContactRepo
import io.reactivex.Single

interface IRepository {
	
	fun get(): Single<List<ContactRepo>>
	
	fun getFromNetwork(): Single<List<ContactRepo>>
}