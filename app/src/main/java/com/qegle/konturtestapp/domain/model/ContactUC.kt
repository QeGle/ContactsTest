package com.qegle.konturtestapp.domain.model

data class ContactUC(val id: String,
                     val name: String,
                     val phone: String,
                     val height: Float,
                     val biography: String,
                     val temperament: TemperamentUC,
                     val educationPeriod: PeriodUC)