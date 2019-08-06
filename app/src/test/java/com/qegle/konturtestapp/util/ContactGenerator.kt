package com.qegle.konturtestapp.util

import com.qegle.konturtestapp.datasource.model.ContactDS
import com.qegle.konturtestapp.datasource.model.PeriodDS
import com.qegle.konturtestapp.repository.mapper.toContactRepoList
import com.qegle.konturtestapp.repository.model.TemperamentRepo

val contactsDSList = listOf(
	ContactDS(
		id = "0",
		name = "name0",
		phone = "+000000111",
		height = 123f,
		biography = "bio",
		temperament = TemperamentRepo.sanguine.name,
		educationPeriod = PeriodDS("2013-07-15T11:44:06-06:00", "2013-07-15T11:44:06-06:00")
	),
	ContactDS(
		id = "1",
		name = "name1",
		phone = "+111111",
		height = 123f,
		biography = "bio",
		temperament = TemperamentRepo.choleric.name,
		educationPeriod = PeriodDS("2013-07-15T11:44:06-06:00", "2013-07-15T11:44:06-06:00")
	),
	ContactDS(
		id = "2",
		name = "name2",
		phone = "+222222333",
		height = 123f,
		biography = "bio",
		temperament = TemperamentRepo.sanguine.name,
		educationPeriod = PeriodDS("2013-07-15T11:44:06-06:00", "2013-07-15T11:44:06-06:00")
	),
	ContactDS(
		id = "3",
		name = "name3",
		phone = "+333333",
		height = 123f,
		biography = "bio",
		temperament = TemperamentRepo.phlegmatic.name,
		educationPeriod = PeriodDS("2013-07-15T11:44:06-06:00", "2013-07-15T11:44:06-06:00")
	),
	ContactDS(
		id = "4",
		name = "name4",
		phone = "+444444111",
		height = 123f,
		biography = "bio",
		temperament = TemperamentRepo.melancholic.name,
		educationPeriod = PeriodDS("2013-07-15T11:44:06-06:00", "2013-07-15T11:44:06-06:00")
	)
)

val contactsRepoList = contactsDSList.toContactRepoList()
