package com.qegle.konturtestapp.repository

import com.qegle.konturtestapp.datasource.IDataSource
import com.qegle.konturtestapp.datasource.model.ContactDS
import com.qegle.konturtestapp.datasource.network.NetworkDataSource.Companion.source1
import com.qegle.konturtestapp.datasource.network.NetworkDataSource.Companion.source2
import com.qegle.konturtestapp.datasource.network.NetworkDataSource.Companion.source3
import com.qegle.konturtestapp.repository.mapper.toContactRepoList
import com.qegle.konturtestapp.repository.model.ContactRepo
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class ContactRepository(
    private val localDataSource: IDataSource,
    private val remoteDataSource: IDataSource,
    private val lastUpdateTime: ValueProvider<Long>
) : IRepository {

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
        return localDataSource.get().map {
            localCopy = it.toContactRepoList()
            return@map localCopy
        }
    }

    private fun fromNetwork(): Single<List<ContactRepo>> {
        val source1 = remoteDataSource.get(source1)
        val source2 = remoteDataSource.get(source2)
        val source3 = remoteDataSource.get(source3)

        return source1
            .zipWith<List<ContactDS>, List<ContactDS>>(source2, BiFunction { t1, t2 -> t1 + t2 })
            .zipWith<List<ContactDS>, List<ContactDS>>(source3, BiFunction { t1, t2 -> t1 + t2 })
            .map {
                lastUpdateTime.setValue(System.currentTimeMillis())
                localDataSource.set(it)
                localCopy = it.toContactRepoList()
                return@map localCopy
            }
    }

    override fun getFromNetwork(): Single<List<ContactRepo>> {
        return fromNetwork()
    }

    companion object {
        const val ONE_MINUTE = 60 * 1000L
    }
}