package com.bakar.carinfo.service

import com.bakar.carinfo.BaseTest
import com.bakar.carinfo.di.WebApiKey
import com.bakar.carinfo.manufacturer.ManufacturerResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class AutoServiceTest : BaseTest() {

    @MockK
    lateinit var apiService: ApiService


    @InjectMockKs
    lateinit var autoService: AutoService

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
    }


    @Test
    fun `should return manufacturer list`() = runBlockingTest {
        coEvery { apiService.manufacturesList(any(), any()) }.returns(testResponse())

        val response = autoService.manufacturesList(1, 1)

        assertThat(response.page).isEqualTo(0)
        assertThat(response.pageSize).isEqualTo(1)
        assertThat(response.totalPageCount).isEqualTo(1)
        assertThat(response.manufacturers.size).isEqualTo(1)
        assertThat(response.manufacturers["3"]).isEqualTo("Audi")
    }

    @Test(expected = Exception::class)
    fun `should throw error when fetching list from remote`() = runBlockingTest {

        coEvery {
            apiService.manufacturesList(
                any(),
                any()
            )
        }.throws(Exception("Mock Exception!!!"))

        autoService.manufacturesList(1, 1)
    }

    @Test
    fun `should return car type list`() = runBlockingTest {
        coEvery { apiService.carTypeList(any()) }.returns(testCarTypeResponse())

        val response = autoService.carTypeList("3")

        assertThat(response["3"]).isEqualTo("A3")
        assertThat(response.size).isEqualTo(1)
    }

    @Test(expected = Exception::class)
    fun `should throw error when fetching list of car types`() = runBlockingTest {

        coEvery {
            apiService.carTypeList(
                any(),
            )
        }.throws(Exception("Mock Exception!!!"))

        autoService.carTypeList("3")
    }


    @Test
    fun `should return list of car building dates`() = runBlockingTest {

        coEvery {
            apiService.carBuiltDates(
                any(),
                any(),
            )
        }.returns(testCarBuiltDateResponse())

        val response = autoService.carBuiltDates("3", "3")

        assertThat(response["1"]).isEqualTo("2011")
        assertThat(response.size).isEqualTo(1)
    }

    @Test(expected = Exception::class)
    fun `should throw error when fetching list of car building dates`() = runBlockingTest {

        coEvery {
            apiService.carBuiltDates(any(), any())
        }.throws(Exception("Mock Exception!!!"))

        autoService.carBuiltDates("3", "3")
    }


    private fun testResponse() = ManufacturerResponse(
        page = 0,
        pageSize = 1,
        totalPageCount = 1,
        manufacturers = mapOf(
            "3" to "Audi"
        )
    )

    private fun testCarTypeResponse() = mapOf(
        "3" to "A3"
    )

    private fun testCarBuiltDateResponse() = mapOf(
        "1" to "2011"
    )


}