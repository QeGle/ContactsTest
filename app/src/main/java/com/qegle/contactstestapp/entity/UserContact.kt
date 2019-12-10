package com.qegle.contactstestapp.entity

data class UserContact(
    val id: String,
    val name: String,
    val phone: String,
    val height: Float,
    val biography: String,
    val temperament: UserTemperament,
    val educationPeriod: UserPeriod
)