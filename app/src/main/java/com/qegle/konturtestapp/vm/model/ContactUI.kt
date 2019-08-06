package com.qegle.konturtestapp.vm.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContactUI(val id: String,
                     val name: String,
                     val phone: String,
                     val height: Float,
                     val biography: String,
                     val temperament: String,
                     val educationPeriod: String) : Parcelable