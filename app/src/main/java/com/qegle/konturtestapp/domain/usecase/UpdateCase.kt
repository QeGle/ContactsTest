package com.qegle.konturtestapp.domain.usecase

import com.qegle.konturtestapp.domain.mapper.toContactUCList
import com.qegle.konturtestapp.domain.model.ContactUC
import com.qegle.konturtestapp.repository.IRepository
import io.reactivex.Single

class UpdateCase(private val repository: IRepository) {
	
	fun getUpdatedData(): Single<List<ContactUC>> = repository.getFromNetwork().map { it.toContactUCList() }
}