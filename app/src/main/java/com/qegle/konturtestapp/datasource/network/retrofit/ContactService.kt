package com.qegle.konturtestapp.datasource.network.retrofit

import com.qegle.konturtestapp.datasource.model.ContactDS
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

private interface IService {
	
	@GET("/SkbkonturMobile/mobile-test-droid/master/json/{filename}")
	fun getContacts(@Path("filename") filename: String): Single<List<ContactDS>>
}

class ContactService(baseUrl: String) {
	private val service = RetrofitConfiguration.createRxService(IService::class.java, baseUrl)
	
	fun getContact(filename: String): Single<List<ContactDS>> {
		return service.getContacts(filename)
	}
}