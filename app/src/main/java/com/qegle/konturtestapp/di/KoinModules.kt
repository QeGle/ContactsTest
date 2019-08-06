package com.qegle.konturtestapp.di

import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import androidx.room.Room
import com.qegle.konturtestapp.datasource.local.LocalDatasource
import com.qegle.konturtestapp.datasource.local.database.AppDatabase
import com.qegle.konturtestapp.datasource.network.NetworkDatasource
import com.qegle.konturtestapp.domain.usecase.GetCase
import com.qegle.konturtestapp.domain.usecase.SearchCase
import com.qegle.konturtestapp.domain.usecase.UpdateCase
import com.qegle.konturtestapp.repository.ContactRepository
import com.qegle.konturtestapp.repository.ContactRepository.Companion.LAST_UPDATE
import com.qegle.konturtestapp.repository.ContactRepository.Companion.ONE_MINUTE
import com.qegle.konturtestapp.repository.IRepository
import com.qegle.konturtestapp.repository.ValueProvider
import com.qegle.konturtestapp.vm.DetailVM
import com.qegle.konturtestapp.vm.ListContactVM
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val dataModule = module {
	single<IRepository> {
		ContactRepository(
			LocalDatasource(get()),
			NetworkDatasource(),
			object : ValueProvider<Long> {
				val preferences = androidApplication().getSharedPreferences("TestApplication", MODE_PRIVATE)
				
				override fun setValue(value: Long) {
					preferences.edit { putLong(LAST_UPDATE, System.currentTimeMillis()) }
				}
				
				override fun getValue(): Long {
					return preferences.getLong(LAST_UPDATE, ONE_MINUTE + 1)
				}
			}
		
		)
	}
	single {
		Room.databaseBuilder(
			androidApplication(),
			AppDatabase::class.java, "database"
		).build()
	}
}

val domainModule = module {
	factory { GetCase(get()) }
	factory { SearchCase(get()) }
	factory { UpdateCase(get()) }
}

val uiModule = module {
	viewModel { DetailVM(androidApplication()) }
	viewModel { ListContactVM(androidApplication(), get(), get(), get()) }
}