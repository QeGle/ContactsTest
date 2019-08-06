package com.qegle.konturtestapp.repository.model

data class ContactRepo(val id: String,
                       val name: String,
                       val phone: String,
                       val height: Float,
                       val biography: String,
                       val temperament: TemperamentRepo,
                       val educationPeriod: PeriodRepo)
