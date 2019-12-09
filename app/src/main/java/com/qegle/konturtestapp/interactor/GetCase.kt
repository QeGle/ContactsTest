package com.qegle.konturtestapp.interactor

import com.qegle.konturtestapp.domain.mapper.toContactUCList
import com.qegle.konturtestapp.domain.model.ContactUC
import com.qegle.konturtestapp.repository.IRepository
import io.reactivex.Single

class GetCase(private val repository: IRepository) {
	
	fun getData(): Single<List<ContactUC>> = repository.get().map { it.toContactUCList() }
}