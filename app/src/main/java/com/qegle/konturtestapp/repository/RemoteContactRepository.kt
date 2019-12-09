package com.qegle.konturtestapp.repository

import com.qegle.konturtestapp.datasource.IDataSource
import com.qegle.konturtestapp.datasource.model.ContactDS
import com.qegle.konturtestapp.datasource.network.NetworkDataSource
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class RemoteContactRepository(private val remoteDataSource: IDataSource) {

    fun getAll(): Single<List<ContactDS>> {
        val source1 = remoteDataSource.get(NetworkDataSource.source1)
        val source2 = remoteDataSource.get(NetworkDataSource.source2)
        val source3 = remoteDataSource.get(NetworkDataSource.source3)

        return source1
            .zipWith<List<ContactDS>, List<ContactDS>>(source2, BiFunction { t1, t2 -> t1 + t2 })
            .zipWith<List<ContactDS>, List<ContactDS>>(source3, BiFunction { t1, t2 -> t1 + t2 })
    }
}