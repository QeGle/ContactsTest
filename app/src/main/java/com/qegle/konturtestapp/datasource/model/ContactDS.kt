package com.qegle.konturtestapp.datasource.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactDS(@PrimaryKey val id: String,
                     val name: String,
                     val phone: String,
                     val height: Float,
                     val biography: String,
                     val temperament: String,
                     @Embedded
                     val educationPeriod: PeriodDS)
