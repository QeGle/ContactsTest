package com.qegle.konturtestapp.repository

import com.qegle.konturtestapp.datasource.IDataSource
import com.qegle.konturtestapp.datasource.model.ContactDS
import io.reactivex.Single

class LocalContactRepository(private val localDataSource: IDataSource) {

    fun getAll(): Single<List<ContactDS>> {
        return localDataSource.get()
    }

    fun set(list: List<ContactDS>) {
        localDataSource.set(list)
    }
}