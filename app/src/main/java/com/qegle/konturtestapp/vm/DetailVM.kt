package com.qegle.konturtestapp.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.qegle.konturtestapp.vm.model.ContactUI

class DetailVM(application: Application) : BaseVM(application) {
	val contact = MutableLiveData<ContactUI>()
	
	fun setData(contact: ContactUI) {
		this.contact.postValue(contact)
	}
}