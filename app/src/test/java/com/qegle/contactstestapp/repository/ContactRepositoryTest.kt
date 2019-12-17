package com.qegle.contactstestapp.repository

import com.google.common.truth.Truth.assertThat
import com.qegle.contactstestapp.database.AppDatabase
import com.qegle.contactstestapp.database.ContactsDao
import com.qegle.contactstestapp.entity.Contact
import com.qegle.contactstestapp.network.ContactsService
import com.qegle.contactstestapp.network.mapper.ContactMapper
import com.qegle.contactstestapp.repository.ContactRepository.Companion.CONTACT_CACHE_PERIOD
import com.qegle.contactstestapp.repository.ContactRepository.Companion.source1
import com.qegle.contactstestapp.repository.ContactRepository.Companion.source2
import com.qegle.contactstestapp.repository.ContactRepository.Companion.source3
import com.qegle.contactstestapp.util.contactsApiList
import com.qegle.contactstestapp.util.contactsList
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito

class ContactRepositoryTest {
    private val serviceApiMock = Mockito.mock(ContactsService::class.java)
    private val syncRepositoryMock = Mockito.mock(SyncRepository::class.java)
    private val databaseMock = Mockito.mock(AppDatabase::class.java)

    private val contactRepository = ContactRepository(serviceApiMock, syncRepositoryMock, databaseMock, ContactMapper())

    private lateinit var contactsDao: ContactsDao

    @Before
    fun createDao() {
        contactsDao = object : ContactsDao {
            override val all: Single<List<Contact>>
                get() = Single.just(contactsList)

            override fun insert(contactDS: Contact) {}
            override fun delete(contactDS: Contact) {}
            override fun deleteAll() {}
            override fun insertAll(users: List<Contact>) {}
        }
    }

    @Test
    fun verifyResultFromLocal() {
        given(databaseMock.contactsDao()).willReturn(contactsDao)

        val list = contactRepository.getContacts(DataFetchStrategy.Local).blockingGet()
        assertThat(list).hasSize(contactsList.size)

        contactRepository.getContacts(DataFetchStrategy.Local)
            .test()
            .assertValue { l ->
                Observable.fromIterable(l)
                    .map { it.id }
                    .toList()
                    .blockingGet() == (contactsList).map { it.id }
            }
    }

    @Test
    fun verifyResultFromRemote() {
        given(databaseMock.contactsDao()).willReturn(contactsDao)

        given(serviceApiMock.getContacts(source1)).willReturn(Single.just(contactsApiList))
        given(serviceApiMock.getContacts(source2)).willReturn(Single.just(contactsApiList))
        given(serviceApiMock.getContacts(source3)).willReturn(Single.just(contactsApiList))

        val list = contactRepository.getContacts(DataFetchStrategy.Remote).blockingGet()
        assertThat(list).hasSize(contactsList.size * 3)

        contactRepository.getContacts(DataFetchStrategy.Remote)
            .test()
            .assertValue { l ->
                Observable.fromIterable(l)
                    .map { it.id }
                    .toList()
                    .blockingGet() == (contactsList + contactsList + contactsList).map { it.id }
            }
    }

    @Test
    fun verifyResultFromLocalByCached() {
        given(databaseMock.contactsDao()).willReturn(contactsDao)

        given(serviceApiMock.getContacts(source1)).willReturn(Single.just(contactsApiList))
        given(serviceApiMock.getContacts(source2)).willReturn(Single.just(contactsApiList))
        given(serviceApiMock.getContacts(source3)).willReturn(Single.just(contactsApiList))

        given(syncRepositoryMock.isContactsSyncRequired()).willReturn(false)
        given(syncRepositoryMock.isContactsSyncExpired(CONTACT_CACHE_PERIOD)).willReturn(false)

        val list = contactRepository.getContacts(DataFetchStrategy.PreferLocal).blockingGet()
        assertThat(list).hasSize(contactsList.size)

        contactRepository.getContacts(DataFetchStrategy.PreferLocal)
            .test()
            .assertValue { l ->
                Observable.fromIterable(l)
                    .map { it.id }
                    .toList()
                    .blockingGet() == (contactsList).map { it.id }
            }
    }

    @Test
    fun verifyResultFromRemoteByCached() {
        given(databaseMock.contactsDao()).willReturn(contactsDao)

        given(serviceApiMock.getContacts(source1)).willReturn(Single.just(contactsApiList))
        given(serviceApiMock.getContacts(source2)).willReturn(Single.just(contactsApiList))
        given(serviceApiMock.getContacts(source3)).willReturn(Single.just(contactsApiList))

        given(syncRepositoryMock.isContactsSyncRequired()).willReturn(true)
        given(syncRepositoryMock.isContactsSyncExpired(CONTACT_CACHE_PERIOD)).willReturn(true)

        val list = contactRepository.getContacts(DataFetchStrategy.PreferLocal).blockingGet()
        assertThat(list).hasSize(contactsList.size * 3)

        contactRepository.getContacts(DataFetchStrategy.PreferLocal)
            .test()
            .assertValue { l ->
                Observable.fromIterable(l)
                    .map { it.id }
                    .toList()
                    .blockingGet() == (contactsList + contactsList + contactsList).map { it.id }
            }
    }
}