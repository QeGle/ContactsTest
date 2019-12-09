package com.qegle.konturtestapp.presentation.base

import android.app.Application
import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable

abstract class BaseVM(application: Application) : AndroidViewModel(application), LifecycleObserver {
	
	val disposable = CompositeDisposable()
	
	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	open fun onStart() {
	}
	
	@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
	open fun onCreate() {
	}
	
	@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
	open fun onResume() {
	}
	
	@OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
	open fun onPause() {
	}
	
	@OnLifecycleEvent(Lifecycle.Event.ON_STOP)
	open fun onStop() {
	}
	
	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	open fun onDestroy() {
	}
	
	override fun onCleared() {
		disposable.dispose()
	}
	
}