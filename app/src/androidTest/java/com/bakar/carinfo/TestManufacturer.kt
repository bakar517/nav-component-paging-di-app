package com.bakar.carinfo

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import com.bakar.carinfo.builtdate.CarBuiltDateViewHolder
import com.bakar.carinfo.cartype.CarTypeViewHolder
import com.bakar.carinfo.manufacturer.ManufacturerViewHolder
import org.junit.Test

class TestManufacturer {
    //end to end testing
    @Test
    fun testAppFlow() {
        //start activity
        ActivityScenario.launch(MainActivity::class.java)

        //check manufacture list is visible
        onView(withId(R.id.list)).check(matches(isDisplayed()))

        //verify content at position 0
        onView(withId(R.id.list))
            .check(matches(hasDescendant(withText("Volkswagen"))))

        //verify content at position 1
        onView(withId(R.id.list))
            .check(matches(hasDescendant(withText("Ford Motor"))))


        //verify content at position 2
        onView(withId(R.id.list))
            .check(matches(hasDescendant(withText("Audi"))))


        //verify content at position 3
        onView(withId(R.id.list))
            .check(matches(hasDescendant(withText("BMW"))))


        //click on item at position 1
        onView(withId(R.id.list)).perform(
            actionOnItemAtPosition<ManufacturerViewHolder>(2, click())
        )

        //verify car type details

        //check Manufacture name is set
        onView(withSubstring("Audi")).check(matches(isDisplayed()))

        //verify car type item at position 1
        onView(withId(R.id.list))
            .check(matches(hasDescendant(withText("A2"))))

        //click on item at position 1
        onView(withId(R.id.list)).perform(
            actionOnItemAtPosition<CarTypeViewHolder>(1, click())
        )

        //verify car built date

        //check Manufacture name and type is set
        onView(withSubstring("Audi")).check(matches(isDisplayed()))
        onView(withSubstring("A2")).check(matches(isDisplayed()))

        //verify built date item at position 0
        onView(withId(R.id.list))
            .check(matches(hasDescendant(withText("2011"))))

        //click on item at position 1
        onView(withId(R.id.list)).perform(
            actionOnItemAtPosition<CarBuiltDateViewHolder>(0, click())
        )

        //verify manufacture name, type and built date

        //check Manufacture name and type is set
        onView(withSubstring("Audi")).check(matches(isDisplayed()))
        onView(withSubstring("A2")).check(matches(isDisplayed()))
        onView(withSubstring("2011")).check(matches(isDisplayed()))

    }
}
