package com.qegle.konturtestapp.datasource.network.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitConfiguration {
	
	fun <S> createRxService(serviceClass: Class<S>, baseUrl: String): S {
		val builder = Retrofit.Builder().baseUrl(baseUrl)
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
		val retrofit = builder.client(createClient()).build()
		return retrofit.create(serviceClass)
	}
	
	private fun createClient(): OkHttpClient {
		val httpClient = OkHttpClient.Builder()
			.connectTimeout(5, TimeUnit.SECONDS)
		httpClient.addInterceptor { chain ->
			val original = chain.request()
			val requestBuilder = original.newBuilder()
				.header("Content-Type", "application/json")
				.method(original.method(), original.body())
			val request = requestBuilder.build()
			chain.proceed(request)
		}
		
		return httpClient.build()
	}
}