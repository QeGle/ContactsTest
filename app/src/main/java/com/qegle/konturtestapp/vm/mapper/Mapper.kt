package com.qegle.konturtestapp.vm.mapper

import com.qegle.konturtestapp.domain.model.ContactUC
import com.qegle.konturtestapp.domain.model.PeriodUC
import com.qegle.konturtestapp.domain.model.TemperamentUC
import com.qegle.konturtestapp.vm.model.ContactUI
import java.text.SimpleDateFormat
import java.util.*


fun List<ContactUC>.toContactUIList() = this.map { it.toContactUC() }

fun ContactUC.toContactUC() = ContactUI(
	id = id,
	name = name,
	phone = phone,
	height = height,
	biography = biography,
	temperament = temperament.toTemperamentUI(),
	educationPeriod = educationPeriod.toPeriodUI()

)

fun PeriodUC.toPeriodUI(): String {
	val pattern = "dd.MM.yyyy"
	val formatter = SimpleDateFormat(pattern, Locale.US)
	val startDate = formatter.format(start)
	val endDate = formatter.format(end)
	return "$startDate - $endDate"
}

fun TemperamentUC.toTemperamentUI() = this.name.capitalize()