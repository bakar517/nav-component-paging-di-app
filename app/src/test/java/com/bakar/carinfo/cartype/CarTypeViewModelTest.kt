package com.bakar.carinfo.cartype

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bakar.baselib.ErrorLog
import com.bakar.baselib.Navigator
import com.bakar.carinfo.BaseTest
import com.bakar.carinfo.getOrAwaitValue
import com.bakar.carinfo.service.AutoService
import com.google.common.truth.Truth.assertThat
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
class CarTypeViewModelTest : BaseTest() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var service: AutoService

    @MockK
    lateinit var navigator: Navigator

    @MockK
    lateinit var errorLog: ErrorLog

    @MockK
    lateinit var args: ManufactureArgs

    lateinit var viewModelTest: CarTypeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
    }

    @Test
    fun `should return the list of car types`() = runBlockingTest {
        every { args.id }.returns("130")
        coEvery { service.carTypeList(any()) }.returns(testCarTypeResponse())

        viewModelTest = CarTypeViewModel(
            service = service,
            navigator = navigator,
            errorLog = errorLog,
            args = args
        )

        val state = viewModelTest.state.getOrAwaitValue()

        val requestState = state.state
        assertThat(requestState is ViewState.RequestState.Data).isTrue()
        assertThat((requestState as ViewState.RequestState.Data).list.size).isEqualTo(1)
        assertThat(requestState.list[0].name).isEqualTo("1er")
        verify(exactly = 1) { args.id }
    }

    @Test
    fun `should throw error when fetch list of car types`() = runBlockingTest {
        val exception = Exception("Mock exception!!!")
        every { args.id }.returns("130")
        coEvery { service.carTypeList(any()) }.throws(exception)

        viewModelTest = CarTypeViewModel(
            service = service,
            navigator = navigator,
            errorLog = errorLog,
            args = args
        )

        val state = viewModelTest.state.getOrAwaitValue()

        assertThat(state.state is ViewState.RequestState.Error).isTrue()
        verify(exactly = 1) { args.id }
        verify(exactly = 1) { errorLog.log(any()) }
    }


    @Test
    fun `query data must not be empty`() = runBlockingTest {
        every { args.id }.returns("130")
        coEvery { service.carTypeList(any()) }.returns(testCarTypeResponse())
        val testQuery = "1er"
        viewModelTest = CarTypeViewModel(
            service = service,
            navigator = navigator,
            errorLog = errorLog,
            args = args
        )
        viewModelTest.performQuery(testQuery)

        val state = viewModelTest.state.getOrAwaitValue()
        assertThat(state.query).isNotEmpty()
        assertThat(state.query).isEqualTo(testQuery)
    }


    @Test
    fun `should navigator call on back button press`() = runBlockingTest {
        every { args.id }.returns("130")
        coEvery { service.carTypeList(any()) }.returns(testCarTypeResponse())

        viewModelTest = CarTypeViewModel(
            service = service,
            navigator = navigator,
            errorLog = errorLog,
            args = args
        )

        val state = viewModelTest.state.getOrAwaitValue()
        state.onTapBackButton()

        verify(exactly = 1) { navigator.navigate(any()) }
    }

    private fun testCarTypeResponse() = mapOf(
        "1er" to "1er"
    )

}