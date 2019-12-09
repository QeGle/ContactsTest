package com.qegle.konturtestapp.datasource.network

import com.qegle.konturtestapp.datasource.IDataSource
import com.qegle.konturtestapp.datasource.model.ContactDS
import com.qegle.konturtestapp.datasource.network.retrofit.ContactService
import io.reactivex.Single

class NetworkDataSource : IDataSource {
	private val service = ContactService(url)
	
	override fun get(name: String): Single<List<ContactDS>> {
		return service.getContact(name)
	}
	
	override fun set(list: List<ContactDS>) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
	
	companion object {
		const val url = "https://raw.githubusercontent.com/"
		const val source1 = "generated-01.json"
		const val source2 = "generated-02.json"
		const val source3 = "generated-03.json"
	}
}