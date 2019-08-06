package com.qegle.konturtestapp.domain.usecase

import com.qegle.konturtestapp.domain.mapper.toContactUCList
import com.qegle.konturtestapp.domain.model.ContactUC
import com.qegle.konturtestapp.repository.IRepository
import io.reactivex.Single

class SearchCase(private val repository: IRepository) {
	
	fun getFilteredData(text: String): Single<List<ContactUC>> =
		repository.get().map { list ->
			list.filter {
				val digitPhone = it.phone.removeSpecialSymbols()
				val textLowerCase = text.toLowerCase()
				val nameLowerCase = it.name.toLowerCase()
				
				nameLowerCase.contains(textLowerCase)
						|| it.phone.contains(textLowerCase)
						|| digitPhone.contains(textLowerCase)
			}.toContactUCList()
		}
	
	
	private fun String.removeSpecialSymbols() = this.replace(Regex("[^+\\d]"), "")
}