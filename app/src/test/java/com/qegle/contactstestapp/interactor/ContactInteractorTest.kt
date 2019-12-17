package com.qegle.contactstestapp.interactor

import com.google.common.truth.Truth.assertThat
import com.qegle.contactstestapp.database.ContactsDao
import com.qegle.contactstestapp.entity.Contact
import com.qegle.contactstestapp.repository.ContactRepository
import com.qegle.contactstestapp.repository.DataFetchStrategy
import com.qegle.contactstestapp.util.contactsList
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito

class ContactInteractorTest {
    private val repositoryMock = Mockito.mock(ContactRepository::class.java)

    private val contactInteractor = ContactInteractor(repositoryMock)

    private val dataFetchStrategy = DataFetchStrategy.PreferLocal

    @Test
    fun verifyResultList() {
        given(repositoryMock.getContacts(dataFetchStrategy)).willReturn(Single.just(contactsList))

        val contactsSingle = contactInteractor.getContacts(dataFetchStrategy)

        val list = contactsSingle.blockingGet()
        assertThat(list).hasSize(contactsList.size)

        contactsSingle
            .test()
            .assertValue { l ->
                Observable.fromIterable(l)
                    .map { it.id }
                    .toList()
                    .blockingGet() == (contactsList).map { it.id }
            }
    }

    @Test
    fun verifyResultSingle() {
        given(repositoryMock.getContacts(dataFetchStrategy)).willReturn(Single.just(contactsList))

        val contactsSingle = contactInteractor.getContact("1", dataFetchStrategy)

        val list = contactsSingle.blockingGet()
        assertThat(list).isNotNull()
    }

    @Test
    fun verifyResultSearchEmptyQuery() {
        given(repositoryMock.getContacts(dataFetchStrategy)).willReturn(Single.just(contactsList))

        val contactsAllSingle = contactInteractor.find("", dataFetchStrategy)

        val list = contactsAllSingle.blockingGet()
        assertThat(list).hasSize(contactsList.size)

        contactsAllSingle
            .test()
            .assertValue { l ->
                Observable.fromIterable(l)
                    .map { it.id }
                    .toList()
                    .blockingGet() == (contactsList).map { it.id }
            }
    }

    @Test
    fun verifyResultSearchNumberQuery() {
        given(repositoryMock.getContacts(dataFetchStrategy)).willReturn(Single.just(contactsList))

        val contactsAllSingle = contactInteractor.find("+333333", dataFetchStrategy)

        val list = contactsAllSingle.blockingGet()
        assertThat(list).hasSize(1)
    }

}