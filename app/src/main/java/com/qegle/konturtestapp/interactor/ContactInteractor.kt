package com.qegle.konturtestapp.interactor

import android.util.Log
import com.qegle.konturtestapp.domain.mapper.toContactUCList
import com.qegle.konturtestapp.domain.model.ContactUC
import com.qegle.konturtestapp.repository.DataFetchStrategy
import com.qegle.konturtestapp.repository.LocalContactRepository
import com.qegle.konturtestapp.repository.RemoteContactRepository
import com.qegle.konturtestapp.repository.ValueProvider
import com.qegle.konturtestapp.repository.mapper.toContactRepoList
import io.reactivex.Single

class ContactInteractor(
    private val localRepository: LocalContactRepository,
    private val remoteRepository: RemoteContactRepository,
    private val lastUpdateTime: ValueProvider<Long>
) {

    private val numberSpecSymbolsRegex = Regex("[^+\\d]")

    fun getContacts(strategy: DataFetchStrategy): Single<List<ContactUC>> {
        Log.d("ContactInteractor", "getContacts ${strategy.name}")
        return when (strategy) {
            DataFetchStrategy.Local -> getContactsLocal()
            DataFetchStrategy.Remote -> getContactsRemote()
            DataFetchStrategy.PreferLocal -> getContactsCached()
        }
    }

    private fun getContactsCached(): Single<List<ContactUC>> {
        Log.d("ContactInteractor", "getContactsCached")
        val isRecentUpdate =
            System.currentTimeMillis() - lastUpdateTime.getValue() <= ONE_MINUTE

        return if (isRecentUpdate) {
            getContactsLocal()
        } else {
            getContactsRemote()
        }
    }

    private fun getContactsRemote(): Single<List<ContactUC>> {
        Log.d("ContactInteractor", "getContactsRemote")
        return remoteRepository.getAll().map {
            localRepository.set(it)
            lastUpdateTime.setValue(System.currentTimeMillis())
            it.toContactRepoList().toContactUCList()
        }
    }

    private fun getContactsLocal(): Single<List<ContactUC>> {
        Log.d("ContactInteractor", "getContactsLocal")
        return localRepository.getAll().map { it.toContactRepoList().toContactUCList() }
    }

    fun find(text: String): Single<List<ContactUC>> {
        Log.d("ContactInteractor", "find $text")
        return getContacts(DataFetchStrategy.PreferLocal).map { list ->
            list.filter {
                val digitPhone = it.phone.replace(numberSpecSymbolsRegex, "")

                it.name.contains(text, true)
                        || it.phone.contains(text, true)
                        || digitPhone.contains(text)
            }
        }
    }

    companion object {
        const val ONE_MINUTE = 60 * 1000L
    }
}