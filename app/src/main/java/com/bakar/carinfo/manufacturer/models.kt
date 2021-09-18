package com.bakar.carinfo.manufacturer

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ManufacturerResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "pageSize") val pageSize: Int,
    @Json(name = "totalPageCount") val totalPageCount: Int,
    @Json(name = "manufacturer") val manufacturers: Map<String, String>
)