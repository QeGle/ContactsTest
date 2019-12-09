package com.qegle.konturtestapp.di

import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import androidx.room.Room
import com.qegle.konturtestapp.datasource.local.LocalDataSource
import com.qegle.konturtestapp.datasource.local.database.AppDatabase
import com.qegle.konturtestapp.datasource.network.NetworkDataSource
import com.qegle.konturtestapp.interactor.ContactInteractor
import com.qegle.konturtestapp.presentation.details.DetailVM
import com.qegle.konturtestapp.presentation.list.ListContactVM
import com.qegle.konturtestapp.repository.ContactRepository.Companion.ONE_MINUTE
import com.qegle.konturtestapp.repository.LocalContactRepository
import com.qegle.konturtestapp.repository.RemoteContactRepository
import com.qegle.konturtestapp.repository.ValueProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val dataModule = module {

    single { LocalContactRepository(LocalDataSource(get())) }

    single { RemoteContactRepository(NetworkDataSource()) }

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java, "database"
        ).build()
    }
}

val domainModule = module {
    factory {
        ContactInteractor(get(), get(), object : ValueProvider<Long> {
            val preferences =
                androidApplication().getSharedPreferences("TestApplication", MODE_PRIVATE)

            override fun setValue(value: Long) {
                preferences.edit { putLong(LAST_UPDATE, System.currentTimeMillis()) }
            }

            override fun getValue(): Long {
                return preferences.getLong(LAST_UPDATE, ONE_MINUTE + 1)
            }
        })
    }
}

val presentationModule = module {
    viewModel {
        DetailVM(
            androidApplication()
        )
    }
    viewModel {
        ListContactVM(
            androidApplication(),
            get()
        )
    }
}

const val LAST_UPDATE = "last_update_time"