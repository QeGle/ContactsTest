package com.qegle.contactstestapp.interactor

import com.qegle.contactstestapp.entity.Contact
import com.qegle.contactstestapp.entity.Period
import com.qegle.contactstestapp.entity.UserContact
import com.qegle.contactstestapp.entity.UserPeriod
import com.qegle.contactstestapp.entity.UserTemperament
import com.qegle.contactstestapp.repository.ContactRepository
import com.qegle.contactstestapp.repository.DataFetchStrategy
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.Locale

class ContactInteractor(private val repository: ContactRepository) {

    private val numberSpecSymbolsRegex = Regex("[^+\\d]")

    fun getContact(id: String, strategy: DataFetchStrategy): Single<UserContact> {
        return getContacts(strategy).map { contacts -> contacts.singleOrNull { it.id == id } }
    }

    fun getContacts(strategy: DataFetchStrategy): Single<List<UserContact>> {
        return repository.getContacts(strategy)
            .map { contacts -> contacts.map { createUserContact(it) } }
    }

    fun find(text: String): Single<List<UserContact>> {
        return getContacts(DataFetchStrategy.PreferLocal).map { list ->
            list.filter {
                val digitPhone = it.phone.replace(numberSpecSymbolsRegex, "")

                it.name.contains(text, true)
                        || it.phone.contains(text, true)
                        || digitPhone.contains(text)
            }
        }
    }

    private fun createUserContact(contact: Contact): UserContact {
        return UserContact(
            id = contact.id,
            name = contact.name,
            phone = contact.phone,
            height = contact.height,
            biography = contact.biography,
            temperament = UserTemperament.fromValue(contact.temperament),
            educationPeriod = createUserPeriod(contact.educationPeriod)
        )
    }

    private fun createUserPeriod(period: Period): UserPeriod {
        val pattern = "yyyy-MM-dd'T'HH:mm:ssXXX"
        val formatter = SimpleDateFormat(pattern, Locale.US)

        val startDate = formatter.parse(period.start)
        val endDate = formatter.parse(period.end)

        return UserPeriod(startDate, endDate)
    }
}