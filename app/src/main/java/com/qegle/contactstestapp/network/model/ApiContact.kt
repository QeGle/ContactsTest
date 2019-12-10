package com.qegle.contactstestapp.network.model

data class ApiContact(
    val id: String,
    val name: String,
    val phone: String,
    val height: Float,
    val biography: String,
    val temperament: String,
    val educationPeriod: ApiPeriod
)