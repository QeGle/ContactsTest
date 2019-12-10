package com.qegle.contactstestapp.network

import com.qegle.contactstestapp.network.model.ApiContact
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ContactsService {
    @GET("/SkbkonturMobile/mobile-test-droid/master/json/{filename}")
    fun getContacts(@Path("filename") filename: String): Single<List<ApiContact>>
}