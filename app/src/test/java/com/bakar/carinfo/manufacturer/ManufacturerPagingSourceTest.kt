package com.bakar.carinfo.manufacturer

import androidx.paging.PagingSource
import com.bakar.baselib.ErrorLog
import com.bakar.carinfo.service.AutoService
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ManufacturerPagingSourceTest {

    @MockK
    lateinit var service: AutoService

    @MockK
    lateinit var errorLog: ErrorLog


    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
    }


    @Test
    fun `should return list of manufacturer page from service`() = runBlockingTest {

        val mockData = testManufacturerResponse()
        coEvery { service.manufacturesList(any(), any()) }.returns(mockData)
        val pagingSource = ManufacturerPagingSource(service = service, errorLog = errorLog)

        assertThat(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        ).isEqualTo(
            PagingSource.LoadResult.Page(
                data = mockData.manufacturers.map {
                    ManufacturerItem(
                        key = it.key,
                        name = it.value
                    )
                },
                prevKey = null,
                nextKey = mockData.page.plus(1)
            )
        )

        verify(exactly = 0) { errorLog.log(any()) }
    }

    @Test
    fun `should throw error when fetch manufacturers from service`() = runBlockingTest {

        val mockException = Exception("Mock exception!!!")
        coEvery { service.manufacturesList(any(), any()) }.throws(mockException)
        val pagingSource = ManufacturerPagingSource(service = service, errorLog = errorLog)
        assertThat(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        ).isEqualTo(
            PagingSource.LoadResult.Error<Int, ManufacturerItem>(mockException)
        )

        verify(exactly = 1) { errorLog.log(any()) }
    }

    private fun testManufacturerResponse() = ManufacturerResponse(
        page = 0,
        pageSize = 2,
        totalPageCount = 2,
        manufacturers = mapOf(
            "130" to "BMW"
        )
    )

}