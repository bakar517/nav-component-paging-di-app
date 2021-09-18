package com.bakar.carinfo.cartype

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ManufactureArgs(
    val id: String,
    val name: String
) : Parcelable