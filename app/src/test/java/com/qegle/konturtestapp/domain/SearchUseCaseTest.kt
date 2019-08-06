package com.qegle.konturtestapp.domain

import com.google.common.truth.Truth.assertThat
import com.qegle.konturtestapp.domain.usecase.SearchCase
import com.qegle.konturtestapp.repository.ContactRepository
import com.qegle.konturtestapp.util.contactsRepoList
import io.reactivex.Single
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito

class SearchUseCaseTest {
	private val mockContactRepository: ContactRepository = Mockito.mock(ContactRepository::class.java)
	private val searchUseCase = SearchCase(mockContactRepository)
	
	@Test
	fun verifyResultWhenSearchCaseHasResult() {
		
		given(mockContactRepository.get()).willReturn(Single.just(contactsRepoList))
		
		val three = searchUseCase.getFilteredData("1").blockingGet()
		assertThat(three).hasSize(3)
		
		val all = searchUseCase.getFilteredData("name").blockingGet()
		assertThat(all).hasSize(contactsRepoList.size)
		
		val two = searchUseCase.getFilteredData("3").blockingGet()
		assertThat(two).hasSize(2)
		
		val one = searchUseCase.getFilteredData("2").blockingGet()
		assertThat(one).hasSize(1)
		
		val none = searchUseCase.getFilteredData("asd").blockingGet()
		assertThat(none).hasSize(0)
	}
}