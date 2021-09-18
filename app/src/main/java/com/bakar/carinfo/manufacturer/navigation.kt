package com.bakar.carinfo.manufacturer

import com.bakar.baselib.Navigator
import com.bakar.carinfo.cartype.ManufactureArgs

fun Navigator.goToCarType(item: ManufacturerItem) =
    navigate(ManufacturerFragmentDirections.actionGoToCarModel(item.toNavArgs()))

private fun ManufacturerItem.toNavArgs() = ManufactureArgs(id = key, name = name)