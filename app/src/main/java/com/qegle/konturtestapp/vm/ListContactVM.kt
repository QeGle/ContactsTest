package com.qegle.konturtestapp.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.qegle.konturtestapp.domain.usecase.GetCase
import com.qegle.konturtestapp.domain.usecase.SearchCase
import com.qegle.konturtestapp.domain.usecase.UpdateCase
import com.qegle.konturtestapp.vm.mapper.toContactUIList
import com.qegle.konturtestapp.vm.model.*
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import java.util.concurrent.TimeUnit


class ListContactVM(application: Application,
                    private val getCase: GetCase,
                    private val searchCase: SearchCase,
                    private val updateCase: UpdateCase) : BaseVM(application) {
	
	val contacts = MutableLiveData<List<ContactUI>>()
	var state = MutableLiveData<State>()
	
	override fun onResume() {
		if (contacts.value == null && state.value !is ErrorState) get()
	}
	
	fun setSearchObservable(observable: Flowable<String>) {
		disposable += observable
			.debounce(300, TimeUnit.MILLISECONDS)
			.distinctUntilChanged()
			.map { query ->
				state.postValue(LoadingState)
				return@map query
			}
			.switchMap { searchCase.getFilteredData(it).toFlowable() }
			.map { it.toContactUIList() }
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribeWith(object : DisposableSubscriber<List<ContactUI>>() {
				override fun onError(e: Throwable) {
					state.postValue(ErrorState())
				}
				
				override fun onNext(t: List<ContactUI>) {
					contacts.postValue(t)
					state.postValue(DoneState)
				}
				
				override fun onComplete() {}
			})
	}
	
	fun get() {
		state.postValue(LoadingState)
		disposable += getCase.getData()
			.map { it.toContactUIList() }
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribeWith(getDisposableSingleObserver())
	}
	
	fun update() {
		state.postValue(LoadingState)
		
		disposable += updateCase.getUpdatedData()
			.map { it.toContactUIList() }
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribeWith(getDisposableSingleObserver())
	}
	
	private fun getDisposableSingleObserver() = object : DisposableSingleObserver<List<ContactUI>?>() {
		override fun onSuccess(t: List<ContactUI>) {
			contacts.postValue(t)
			state.postValue(DoneState)
		}
		
		override fun onError(e: Throwable) {
			state.postValue(ErrorState())
			e.printStackTrace()
		}
	}
}