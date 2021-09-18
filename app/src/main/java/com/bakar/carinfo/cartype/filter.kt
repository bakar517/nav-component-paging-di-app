package com.bakar.carinfo.cartype

object SearchManager {

    fun filterByName(query: String, list: List<CarTypeItem>): List<CarTypeItem> =
        list.filter { it.name.contains(query, ignoreCase = true) }
}