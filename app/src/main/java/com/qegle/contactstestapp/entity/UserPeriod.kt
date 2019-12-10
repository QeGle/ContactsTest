package com.qegle.contactstestapp.entity

import java.text.SimpleDateFormat
import java.util.*

data class UserPeriod(
    val start: Date,
    val end: Date
) {
    override fun toString(): String {
        val pattern = "dd.MM.yyyy"
        val formatter = SimpleDateFormat(pattern, Locale.US)
        val startDate = formatter.format(start)
        val endDate = formatter.format(end)
        return "$startDate - $endDate"
    }
}