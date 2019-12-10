package com.qegle.contactstestapp.util

import com.qegle.contactstestapp.repository.mapper.toContactRepoList
import com.qegle.contactstestapp.repository.model.ContactRepo
import com.qegle.contactstestapp.repository.model.PeriodRepo
import com.qegle.contactstestapp.repository.model.TemperamentRepo

val contactsDSList = listOf(
	ContactDS(
		id = "0",
		name = "name0",
		phone = "+000000111",
		height = 123f,
		biography = "bio",
		temperament = TemperamentRepo.sanguine.name,
		educationPeriod = PeriodDS(
			"2013-07-15T11:44:06-06:00",
			"2013-07-15T11:44:06-06:00"
		)
	),
	ContactDS(
		id = "1",
		name = "name1",
		phone = "+111111",
		height = 123f,
		biography = "bio",
		temperament = TemperamentRepo.choleric.name,
		educationPeriod = PeriodDS(
			"2013-07-15T11:44:06-06:00",
			"2013-07-15T11:44:06-06:00"
		)
	),
	ContactDS(
		id = "2",
		name = "name2",
		phone = "+222222333",
		height = 123f,
		biography = "bio",
		temperament = TemperamentRepo.sanguine.name,
		educationPeriod = PeriodDS(
			"2013-07-15T11:44:06-06:00",
			"2013-07-15T11:44:06-06:00"
		)
	),
	ContactDS(
		id = "3",
		name = "name3",
		phone = "+333333",
		height = 123f,
		biography = "bio",
		temperament = TemperamentRepo.phlegmatic.name,
		educationPeriod = PeriodDS(
			"2013-07-15T11:44:06-06:00",
			"2013-07-15T11:44:06-06:00"
		)
	),
	ContactDS(
		id = "4",
		name = "name4",
		phone = "+444444111",
		height = 123f,
		biography = "bio",
		temperament = TemperamentRepo.melancholic.name,
		educationPeriod = PeriodDS(
			"2013-07-15T11:44:06-06:00",
			"2013-07-15T11:44:06-06:00"
		)
	)
)

val contactsRepoList = contactsDSList.toContactRepoList()

fun List<ContactDS>.toContactRepoList() = this.map { it.toContactRepo() }

fun ContactDS.toContactRepo() = ContactRepo(
	id = id,
	name = name,
	phone = phone,
	height = height,
	biography = biography,
	temperament = TemperamentRepo.valueOf(temperament),
	educationPeriod = educationPeriod.toPeriodRepo()

)

fun PeriodDS.toPeriodRepo(): PeriodRepo {
	val pattern = "yyyy-MM-dd'T'HH:mm:ssXXX"
	val formatter = SimpleDateFormat(pattern, Locale.US)

	val startDate = formatter.parse(start)
	val endDate = formatter.parse(end)

	return PeriodRepo(startDate, endDate)
}
