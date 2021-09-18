package com.bakar.carinfo.di

import com.bakar.carinfo.manufacturer.ManufacturerResponse
import com.bakar.carinfo.service.ApiService

class MockApiService : ApiService {
    override suspend fun manufacturesList(
        page: Int,
        pageSize: Int,
    ): ManufacturerResponse = ManufacturerResponse(
        page = 0,
        pageSize = 1,
        totalPageCount = 1,
        manufacturers = mapOf(
            "1" to "Volkswagen",
            "2" to "Ford Motor",
            "3" to "Audi",
            "4" to "BMW"
        )
    )

    override suspend fun carTypeList(manufacturerId: String): Map<String, String> =
        mapOf(
            "1" to "A1",
            "2" to "A2",
            "3" to "A3",
            "4" to "A4",
        )

    override suspend fun carBuiltDates(
        manufacturerId: String,
        carTypeId: String,
    ): Map<String, String> = mapOf(
        "1" to "2011",
        "2" to "2012",
        "3" to "2013",
        "4" to "2014",
        "5" to "2015",
    )
}