package com.qegle.konturtestapp.domain

import com.google.common.truth.Truth.assertThat
import com.qegle.konturtestapp.interactor.GetCase
import com.qegle.konturtestapp.repository.ContactRepository
import com.qegle.konturtestapp.util.contactsRepoList
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class GetUseCaseTest {
	private val mockContactRepository: ContactRepository = mock(ContactRepository::class.java)
	private val getUseCase =
        GetCase(mockContactRepository)
	
	@Test
	fun verifyResultWhenGetCaseReturnData() {
		
		given(mockContactRepository.get()).willReturn(Single.just(contactsRepoList))
		
		val list2 = getUseCase.getData().blockingGet()
		
		assertThat(list2).hasSize(contactsRepoList.size)
		
		getUseCase.getData()
			.test()
			.assertValue { l ->
				Observable.fromIterable(l)
					.map { it.id }
					.toList()
					.blockingGet() == contactsRepoList.map { it.id }
			}
	}
}