package com.demir.yasin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListModel(
    val company :String,
    val installationNumber:String,
    val contractAccountNumber:String,
    val amount:String,
    val address:String
):Parcelable{

}
