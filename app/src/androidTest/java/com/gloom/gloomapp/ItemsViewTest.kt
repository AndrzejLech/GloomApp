package com.gloom.gloomapp

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.gloom.gloomapp.View.PartyActivity
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test

class ItemsViewTest {
    @get:Rule
    val mActivityTestRule: ActivityTestRule<PartyActivity> = ActivityTestRule<PartyActivity>(PartyActivity::class.java)

    @Test
    fun navigateToItems() {
        onView(withId(R.id.ItemsButton)).perform(ViewActions.click())
        onData(CoreMatchers.anything()).inAdapterView(withId(R.id.list)).atPosition(0).perform(ViewActions.click())
    }
}
