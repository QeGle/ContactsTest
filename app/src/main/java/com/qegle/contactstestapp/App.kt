package com.qegle.contactstestapp

import android.app.Application
import com.qegle.contactstestapp.di.dataModule
import com.qegle.contactstestapp.di.domainModule
import com.qegle.contactstestapp.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(dataModule, domainModule, presentationModule))
        }
    }
}