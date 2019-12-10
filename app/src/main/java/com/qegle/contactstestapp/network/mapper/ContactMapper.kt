package com.qegle.contactstestapp.network.mapper

import com.qegle.contactstestapp.entity.Contact
import com.qegle.contactstestapp.entity.Period
import com.qegle.contactstestapp.network.model.ApiContact
import com.qegle.contactstestapp.network.model.ApiPeriod

class ContactMapper {

    fun map(apiContact: ApiContact): Contact {
        return Contact(
            id = apiContact.id,
            name = apiContact.name,
            phone = apiContact.phone,
            height = apiContact.height,
            biography = apiContact.biography,
            temperament = apiContact.temperament,
            educationPeriod = apiContact.educationPeriod.toPeriod()
        )
    }

    private fun ApiPeriod.toPeriod(): Period {
        return Period(start, end)
    }
}