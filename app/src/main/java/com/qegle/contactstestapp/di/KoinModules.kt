package com.qegle.contactstestapp.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.room.Room
import com.qegle.contactstestapp.database.AppDatabase
import com.qegle.contactstestapp.interactor.ContactInteractor
import com.qegle.contactstestapp.network.ContactsService
import com.qegle.contactstestapp.network.RetrofitConfiguration
import com.qegle.contactstestapp.network.mapper.ContactMapper
import com.qegle.contactstestapp.presentation.details.DetailViewModel
import com.qegle.contactstestapp.presentation.list.ListContactViewModel
import com.qegle.contactstestapp.repository.ContactRepository
import com.qegle.contactstestapp.repository.SyncRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {

    single { createContactsService(url) }
    single { androidApplication().createDatabase() }

    single { ContactMapper() }
    single { SyncRepository(androidApplication().getSharedPreferences(PREFS_NAME, MODE_PRIVATE)) }

    single { ContactRepository(get(), get(), get(), get()) }
}

val domainModule = module {
    single { ContactInteractor(get()) }
}

val presentationModule = module {
    viewModel { (id: String) -> DetailViewModel(id, get()) }
    viewModel { ListContactViewModel(get()) }
}

private fun createContactsService(url: String): ContactsService {
    return RetrofitConfiguration.createRxService(ContactsService::class.java, url)
}

private fun Context.createDatabase(): AppDatabase {
    return Room.databaseBuilder(this, AppDatabase::class.java, "database").build()
}

const val url = "https://raw.githubusercontent.com/"
const val PREFS_NAME = "TestApplication"