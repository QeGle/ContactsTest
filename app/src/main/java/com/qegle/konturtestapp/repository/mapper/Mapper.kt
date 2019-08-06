package com.qegle.konturtestapp.repository.mapper

import com.qegle.konturtestapp.datasource.model.ContactDS
import com.qegle.konturtestapp.datasource.model.PeriodDS
import com.qegle.konturtestapp.repository.model.ContactRepo
import com.qegle.konturtestapp.repository.model.PeriodRepo
import com.qegle.konturtestapp.repository.model.TemperamentRepo
import java.text.SimpleDateFormat
import java.util.*

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