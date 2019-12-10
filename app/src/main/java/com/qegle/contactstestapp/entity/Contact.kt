package com.qegle.contactstestapp.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey val id: String,
    val name: String,
    val phone: String,
    val height: Float,
    val biography: String,
    val temperament: String,
    @Embedded val educationPeriod: Period
)