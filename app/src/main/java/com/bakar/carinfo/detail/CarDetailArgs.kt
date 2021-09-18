package com.bakar.carinfo.detail

import android.os.Parcelable
import com.bakar.carinfo.builtdate.CarTypeArgs
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarDetailArgs(
    val id: String,
    val name: String,
    val carType: CarTypeArgs
) : Parcelable
