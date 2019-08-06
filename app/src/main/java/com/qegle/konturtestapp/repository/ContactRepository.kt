package com.qegle.konturtestapp.repository

import com.qegle.konturtestapp.datasource.IDatasource
import com.qegle.konturtestapp.datasource.model.ContactDS
import com.qegle.konturtestapp.datasource.network.NetworkDatasource.Companion.source1
import com.qegle.konturtestapp.datasource.network.NetworkDatasource.Companion.source2
import com.qegle.konturtestapp.datasource.network.NetworkDatasource.Companion.source3
import com.qegle.konturtestapp.repository.mapper.toContactRepoList
import com.qegle.konturtestapp.repository.model.ContactRepo
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class ContactRepository(private val localDatasource: IDatasource,
                        private val remoteDatasource: IDatasource,
                        private val lastUpdateTime: ValueProvider<Long>) : IRepository {
	
	private var localCopy = emptyList<ContactRepo>()
	
	override fun get(): Single<List<ContactRepo>> {
		if (localCopy.isNotEmpty()) return Single.just(localCopy)
		val lastUpdate = lastUpdateTime.getValue()
		
		return if (System.currentTimeMillis() - lastUpdate > ONE_MINUTE)
			fromNetwork()
		else
			fromLocal()
	}
	
	private fun fromLocal(): Single<List<ContactRepo>> {
		return localDatasource.get().map {
			localCopy = it.toContactRepoList()
			return@map localCopy
		}
	}
	
	private fun fromNetwork(): Single<List<ContactRepo>> {
		val source1 = remoteDatasource.get(source1)
		val source2 = remoteDatasource.get(source2)
		val source3 = remoteDatasource.get(source3)
		
		return source1
			.zipWith<List<ContactDS>, List<ContactDS>>(source2, BiFunction { t1, t2 -> t1 + t2 })
			.zipWith<List<ContactDS>, List<ContactDS>>(source3, BiFunction { t1, t2 -> t1 + t2 })
			.map {
				lastUpdateTime.setValue(System.currentTimeMillis())
				localDatasource.set(it)
				localCopy = it.toContactRepoList()
				return@map localCopy
			}
	}
	
	override fun getFromNetwork(): Single<List<ContactRepo>> {
		return fromNetwork()
	}
	
	companion object {
		const val ONE_MINUTE = 60 * 1000L
		const val LAST_UPDATE = "last_update_time"
	}
}