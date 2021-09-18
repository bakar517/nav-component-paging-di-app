package com.bakar.carinfo.service

import com.bakar.carinfo.manufacturer.ManufacturerResponse
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("manufacturer.json")
    suspend fun manufacturesList(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): ManufacturerResponse

    @Wrapped(path = ["car-types"])
    @GET("car-types/{manufactureId}/types.json")
    suspend fun carTypeList(
        @Path("manufactureId") manufactureId: String,
    ): Map<String, String>

    @Wrapped(path = ["built-dates"])
    @GET("car-types/{manufactureId}/{carTypeId}/dates.json")
    suspend fun carBuiltDates(
        @Path("manufactureId") manufactureId: String,
        @Path("carTypeId") carTypeId: String,
    ): Map<String, String>

}