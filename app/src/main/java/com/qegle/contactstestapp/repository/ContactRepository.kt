package com.qegle.contactstestapp.repository

import com.qegle.contactstestapp.database.AppDatabase
import com.qegle.contactstestapp.entity.Contact
import com.qegle.contactstestapp.extensions.subscribeOnIo
import com.qegle.contactstestapp.network.ContactsService
import com.qegle.contactstestapp.network.mapper.ContactMapper
import com.qegle.contactstestapp.network.model.ApiContact
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

class ContactRepository(
    private val serviceApi: ContactsService,
    private val syncRepository: SyncRepository,
    private val database: AppDatabase,
    private val contactMapper: ContactMapper
) {

    fun getContacts(strategy: DataFetchStrategy): Single<List<Contact>> {
        return when (strategy) {
            DataFetchStrategy.Local -> getContactsLocal()
            DataFetchStrategy.Remote -> getContactsRemote()
            DataFetchStrategy.PreferLocal -> getContactsCached()
        }
    }

    private fun getContactsCached(): Single<List<Contact>> {
        return when {
            syncRepository.isContactsSyncRequired() -> getContactsRemote()
            syncRepository.isContactsSyncExpired(CACHE_PERIOD) -> getContactsRemote().onErrorResumeNext { getContactsLocal() }
            else -> getContactsLocal()
        }
    }

    private fun getContactsLocal(): Single<List<Contact>> {
        return database.contactsDao().all
    }

    private fun getContactsRemote(): Single<List<Contact>> {
        val source1 = serviceApi.getContacts(source1)
        val source2 = serviceApi.getContacts(source2)
        val source3 = serviceApi.getContacts(source3)

        val contactSingle = source1
            .zipWith<List<ApiContact>, List<ApiContact>>(source2, BiFunction { t1, t2 -> t1 + t2 })
            .zipWith<List<ApiContact>, List<ApiContact>>(source3, BiFunction { t1, t2 -> t1 + t2 })
            .subscribeOnIo()

        return contactSingle
            .map { deleteEntities();writeEntities(it) }
            .doOnSuccess { syncRepository.updateContactsSync() }
    }

    private fun deleteEntities() {
        database.contactsDao().deleteAll()
    }

    private fun writeEntities(entities: List<ApiContact>): List<Contact> {
        return entities
            .map { contactMapper.map(it) }
            .apply { database.contactsDao().insertAll(this) }
    }

    companion object {
        const val source1 = "generated-01.json"
        const val source2 = "generated-02.json"
        const val source3 = "generated-03.json"
        private val CACHE_PERIOD = TimeUnit.MINUTES.toMillis(1)
    }
}