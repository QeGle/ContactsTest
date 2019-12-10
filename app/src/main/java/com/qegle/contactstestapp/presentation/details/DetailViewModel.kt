package com.qegle.contactstestapp.presentation.details

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.qegle.contactstestapp.entity.UserContact
import com.qegle.contactstestapp.extensions.observeOnUi
import com.qegle.contactstestapp.interactor.ContactInteractor
import com.qegle.contactstestapp.presentation.base.BaseViewModel
import com.qegle.contactstestapp.repository.DataFetchStrategy

class DetailViewModel(
    val id: String,
    private val contactInteractor: ContactInteractor
) : BaseViewModel() {

    val isLoading = ObservableBoolean()
    val name = ObservableField<String>()
    val phone = ObservableField<String>()
    val biography = ObservableField<String>()
    val temperament = ObservableField<String>()
    val educationPeriod = ObservableField<String>()

    init {
        loadInfo()
    }

    private fun loadInfo() {
        contactInteractor.getContact(id, DataFetchStrategy.PreferLocal)
            .observeOnUi()
            .doOnSubscribe { isLoading.set(true) }
            .doAfterTerminate { isLoading.set(false) }
            .subscribe(
                { showData(it) },
                { errorLiveData.postValue(it) }
            )
            .disposeLater()
    }

    private fun showData(contact: UserContact) {
        name.set(contact.name)
        phone.set(contact.phone)
        biography.set(contact.biography)
        temperament.set(contact.temperament.name)
        educationPeriod.set(contact.educationPeriod.toString())
    }
}