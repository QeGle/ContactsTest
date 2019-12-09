package com.qegle.konturtestapp.presentation.details

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.qegle.konturtestapp.presentation.base.BaseVM
import com.qegle.konturtestapp.vm.model.ContactUI

class DetailVM(application: Application) : BaseVM(application) {
	val contact = MutableLiveData<ContactUI>()
	
	fun setData(contact: ContactUI) {
		this.contact.postValue(contact)
	}
}