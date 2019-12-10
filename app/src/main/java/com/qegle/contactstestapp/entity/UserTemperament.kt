package com.qegle.contactstestapp.entity

enum class UserTemperament {
    melancholic,
    phlegmatic,
    sanguine,
    choleric,
    unknown;

    companion object {
        fun fromValue(value: String): UserTemperament {
            return values().find { it.name.equals(value, true) } ?: unknown
        }
    }
}