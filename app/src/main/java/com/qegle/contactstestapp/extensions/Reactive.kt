package com.qegle.contactstestapp.extensions

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun Completable.subscribeOnIo(): Completable {
    return subscribeOn(Schedulers.io())
}

fun Completable.observeOnUi(): Completable {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.subscribeOnIo(): Observable<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Observable<T>.observeOnUi(): Observable<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.subscribeOnIo(): Single<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Single<T>.observeOnUi(): Single<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.subscribeOnIo(): Flowable<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Flowable<T>.observeOnUi(delayError: Boolean = true): Flowable<T> {
    return observeOn(AndroidSchedulers.mainThread(), delayError)
}

fun <T> Maybe<T>.subscribeOnIo(): Maybe<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Maybe<T>.observeOnUi(): Maybe<T> {
    return observeOn(AndroidSchedulers.mainThread())
}