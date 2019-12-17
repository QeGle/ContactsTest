package com.qegle.contactstestapp.util

import com.qegle.contactstestapp.entity.Contact
import com.qegle.contactstestapp.entity.Period
import com.qegle.contactstestapp.entity.UserContact
import com.qegle.contactstestapp.entity.UserPeriod
import com.qegle.contactstestapp.entity.UserTemperament
import com.qegle.contactstestapp.network.model.ApiContact
import com.qegle.contactstestapp.network.model.ApiPeriod
import java.text.SimpleDateFormat
import java.util.Locale

val contactsList = listOf(
    Contact(
        id = "0",
        name = "name0",
        phone = "+000000111",
        height = 123f,
        biography = "bio",
        temperament = UserTemperament.sanguine.name,
        educationPeriod = Period(
            "2013-07-15T11:44:06-06:00",
            "2013-07-15T11:44:06-06:00"
        )
    ),
    Contact(
        id = "1",
        name = "name1",
        phone = "+111111",
        height = 123f,
        biography = "bio",
        temperament = UserTemperament.choleric.name,
        educationPeriod = Period(
            "2013-07-15T11:44:06-06:00",
            "2013-07-15T11:44:06-06:00"
        )
    ),
    Contact(
        id = "2",
        name = "name2",
        phone = "+222222333",
        height = 123f,
        biography = "bio",
        temperament = UserTemperament.sanguine.name,
        educationPeriod = Period(
            "2013-07-15T11:44:06-06:00",
            "2013-07-15T11:44:06-06:00"
        )
    ),
    Contact(
        id = "3",
        name = "name3",
        phone = "+333333",
        height = 123f,
        biography = "bio",
        temperament = UserTemperament.phlegmatic.name,
        educationPeriod = Period(
            "2013-07-15T11:44:06-06:00",
            "2013-07-15T11:44:06-06:00"
        )
    ),
    Contact(
        id = "4",
        name = "name4",
        phone = "+444444111",
        height = 123f,
        biography = "bio",
        temperament = UserTemperament.melancholic.name,
        educationPeriod = Period(
            "2013-07-15T11:44:06-06:00",
            "2013-07-15T11:44:06-06:00"
        )
    )
)

val contactsApiList = contactsList.toApiContactList()

val userContactsList = contactsList.toUserContactList()

fun List<Contact>.toApiContactList() = this.map { it.toApiContact() }

fun List<Contact>.toUserContactList() = this.map { it.toUserContact() }

fun Contact.toApiContact() = ApiContact(
    id = id,
    name = name,
    phone = phone,
    height = height,
    biography = biography,
    temperament = UserTemperament.valueOf(temperament).name,
    educationPeriod = educationPeriod.toApiPeriod()

)

fun Period.toApiPeriod(): ApiPeriod {
    return ApiPeriod(start, end)
}

private fun Contact.toUserContact(): UserContact {
    return UserContact(
        id = id,
        name = name,
        phone = phone,
        height = height,
        biography = biography,
        temperament = UserTemperament.fromValue(temperament),
        educationPeriod = educationPeriod.toUserPeriod()
    )
}

private fun Period.toUserPeriod(): UserPeriod {
    val pattern = "yyyy-MM-dd'T'HH:mm:ssXXX"
    val formatter = SimpleDateFormat(pattern, Locale.US)

    val startDate = formatter.parse(start)
    val endDate = formatter.parse(end)

    return UserPeriod(startDate, endDate)
}
