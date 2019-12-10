package com.qegle.contactstestapp.presentation.base

import androidx.lifecycle.ViewModel
import com.qegle.contactstestapp.presentation.common.SingleLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    val errorLiveData = SingleLiveData<Throwable>()

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    protected fun Disposable.disposeLater() {
        compositeDisposable.add(this)
    }
}