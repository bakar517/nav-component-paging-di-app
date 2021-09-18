package com.bakar.carinfo.builtdate

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bakar.baselib.ErrorLog
import com.bakar.baselib.Navigator
import com.bakar.carinfo.BaseTest
import com.bakar.carinfo.getOrAwaitValue
import com.bakar.carinfo.service.AutoService
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class CarBuiltDateViewModelTest() : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var service: AutoService

    @MockK
    lateinit var navigator: Navigator

    @MockK
    lateinit var errorLog: ErrorLog

    @MockK
    lateinit var args: CarTypeArgs

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
    }


    @Test
    fun `should return the list of car types`() = runBlockingTest {
        every { args.carTypeId }.returns("1er")
        every { args.manufacturer.id }.returns("130")
        coEvery { service.carBuiltDates(any(), any()) }.returns(testCarBuiltDatesResponse())

        val viewModelTest = CarBuiltDateViewModel(
            service = service,
            errorLog = errorLog,
            args = args,
            navigator = navigator
        )

        val state = viewModelTest.state.getOrAwaitValue()
        Truth.assertThat(state.requestState is ViewState.RequestState.Data).isTrue()
        Truth.assertThat((state.requestState as ViewState.RequestState.Data).list.size).isEqualTo(1)
        Truth.assertThat((state.requestState as ViewState.RequestState.Data).list[0].date)
            .isEqualTo("2010")
        verify(exactly = 1) { args.carTypeId }
        verify(exactly = 1) { args.manufacturer.id }
    }

    @Test
    fun `should throw error when fetch list of car types`() = runBlockingTest {
        every { args.carTypeId }.returns("1er")
        every { args.manufacturer.id }.returns("130")
        coEvery { service.carBuiltDates(any(), any()) }.throws(Exception("Mock exception!!!"))

        val viewModelTest = CarBuiltDateViewModel(
            service = service,
            errorLog = errorLog,
            args = args,
            navigator = navigator
        )

        val state = viewModelTest.state.getOrAwaitValue()

        Truth.assertThat(state.requestState is ViewState.RequestState.Error).isTrue()
        verify(exactly = 1) { args.carTypeId }
        verify(exactly = 1) { args.manufacturer.id }
        verify(exactly = 1) { errorLog.log(any()) }
    }

    @Test
    fun `should navigator call on back button press`() = runBlockingTest {
        every { args.carTypeId }.returns("1er")
        every { args.manufacturer.id }.returns("130")
        coEvery { service.carBuiltDates(any(), any()) }.returns(testCarBuiltDatesResponse())

        val viewModelTest = CarBuiltDateViewModel(
            service = service,
            navigator = navigator,
            errorLog = errorLog,
            args = args
        )

        val state = viewModelTest.state.getOrAwaitValue()
        state.onTapBackButton()

        verify(exactly = 1) { navigator.navigate(any()) }
    }


    private fun testCarBuiltDatesResponse() = mapOf(
        "2010" to "2010"
    )

}