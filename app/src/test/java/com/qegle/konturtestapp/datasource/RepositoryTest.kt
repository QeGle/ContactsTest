package com.qegle.konturtestapp.datasource

import com.google.common.truth.Truth.assertThat
import com.qegle.konturtestapp.datasource.local.LocalDatasource
import com.qegle.konturtestapp.datasource.network.NetworkDatasource
import com.qegle.konturtestapp.datasource.network.NetworkDatasource.Companion.source1
import com.qegle.konturtestapp.datasource.network.NetworkDatasource.Companion.source2
import com.qegle.konturtestapp.datasource.network.NetworkDatasource.Companion.source3
import com.qegle.konturtestapp.repository.ContactRepository
import com.qegle.konturtestapp.util.LongProvider
import com.qegle.konturtestapp.util.contactsDSList
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito

class RepositoryTest {
	private val localDatasourceMock = Mockito.mock(LocalDatasource::class.java)
	private val remoteDatasourceMock = Mockito.mock(NetworkDatasource::class.java)
	private val longDelegate = Mockito.mock(LongProvider::class.java)
	
	private val contactRepository = ContactRepository(localDatasourceMock, remoteDatasourceMock, longDelegate)
	
	
	@Test
	fun verifyResultWhenLocalDatasourceUsed() {
		given(localDatasourceMock.get()).willReturn(Single.just(contactsDSList))
		
		given(longDelegate.getValue()).willReturn(System.currentTimeMillis())
		
		val list = contactRepository.get().blockingGet()
		assertThat(list).hasSize(contactsDSList.size)
		
		contactRepository.get()
			.test()
			.assertValue { l ->
				Observable.fromIterable(l)
					.map { it.id }
					.toList()
					.blockingGet() == (contactsDSList).map { it.id }
			}
	}
	
	@Test
	fun verifyResultWhenRemoteDatasourceUsed() {
		given(remoteDatasourceMock.get(source1)).willReturn(Single.just(contactsDSList))
		given(remoteDatasourceMock.get(source2)).willReturn(Single.just(contactsDSList))
		given(remoteDatasourceMock.get(source3)).willReturn(Single.just(contactsDSList))
		
		given(longDelegate.getValue()).willReturn(0)
		
		val list = contactRepository.get().blockingGet()
		assertThat(list).hasSize(contactsDSList.size*3)
		
		contactRepository.get()
			.test()
			.assertValue { l ->
				Observable.fromIterable(l)
					.map { it.id }
					.toList()
					.blockingGet() == (contactsDSList + contactsDSList + contactsDSList).map { it.id }
			}
	}
	
	@Test
	fun verifyResultFromNetworkWhenReturnData() {
		
		given(remoteDatasourceMock.get(source1)).willReturn(Single.just(contactsDSList))
		given(remoteDatasourceMock.get(source2)).willReturn(Single.just(contactsDSList))
		given(remoteDatasourceMock.get(source3)).willReturn(Single.just(contactsDSList))
		
		val list = contactRepository.getFromNetwork().blockingGet()
		assertThat(list).hasSize(contactsDSList.size * 3)
		
		contactRepository.getFromNetwork()
			.test()
			.assertValue { l ->
				Observable.fromIterable(l)
					.map { it.id }
					.toList()
					.blockingGet() == (contactsDSList + contactsDSList + contactsDSList).map { it.id }
			}
	}
}