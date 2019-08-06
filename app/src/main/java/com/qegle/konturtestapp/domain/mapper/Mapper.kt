package com.qegle.konturtestapp.domain.mapper

import com.qegle.konturtestapp.domain.model.ContactUC
import com.qegle.konturtestapp.domain.model.PeriodUC
import com.qegle.konturtestapp.domain.model.TemperamentUC
import com.qegle.konturtestapp.repository.model.ContactRepo
import com.qegle.konturtestapp.repository.model.PeriodRepo
import com.qegle.konturtestapp.repository.model.TemperamentRepo


fun List<ContactRepo>.toContactUCList() = this.map { it.toContactUC() }

fun ContactRepo.toContactUC() = ContactUC(
	id = id,
	name = name,
	phone = phone,
	height = height,
	biography = biography,
	temperament = temperament.toTemperamentUC(),
	educationPeriod = educationPeriod.toPeriodUC()
)

fun PeriodRepo.toPeriodUC(): PeriodUC {
	return PeriodUC(start, end)
}

fun TemperamentRepo.toTemperamentUC() = when (this) {
	TemperamentRepo.melancholic -> TemperamentUC.melancholic
	TemperamentRepo.phlegmatic -> TemperamentUC.phlegmatic
	TemperamentRepo.sanguine -> TemperamentUC.sanguine
	TemperamentRepo.choleric -> TemperamentUC.choleric
}