package com.bakar.carinfo.service

import com.bakar.carinfo.di.Dispatchers
import com.bakar.carinfo.di.WebApiKey
import kotlinx.coroutines.invoke
import javax.inject.Inject

open class AutoService @Inject constructor(
    private val apiService: ApiService,
    private val dispatchers: Dispatchers,
) {

    suspend fun manufacturesList(page: Int, pageSize: Int) = dispatchers.IO {
        apiService.manufacturesList(page = page, pageSize = pageSize)
    }

    suspend fun carTypeList(manufacturerId: String) = dispatchers.IO {
        apiService.carTypeList(manufactureId = manufacturerId)
    }

    suspend fun carBuiltDates(manufacturerId: String, carTypeId: String) = dispatchers.IO {
        apiService.carBuiltDates(
            manufactureId = manufacturerId,
            carTypeId = carTypeId,
        )
    }


}