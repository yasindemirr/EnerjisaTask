package com.demir.yasin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
data class Invoice(
    val amount: String,
    val currency: String,
    val documentNumber: String,
    val dueDate: String,
    val installationNumber: String,
    val legacyContractAccountNumber: String
)