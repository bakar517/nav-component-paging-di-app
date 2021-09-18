package com.bakar.carinfo.builtdate

import android.os.Parcelable
import com.bakar.carinfo.cartype.ManufactureArgs
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarTypeArgs(
    val carTypeId: String,
    val carTypeName: String,
    val manufacturer: ManufactureArgs
) : Parcelable