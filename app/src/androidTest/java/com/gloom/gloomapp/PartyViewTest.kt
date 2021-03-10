package com.gloom.gloomapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.gloom.gloomapp.View.PartyActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PartyViewTest {
    companion object {
        private const val SinglePartyName: String = "Single Test Party"
        private const val ManyPartiesName: String = "Many Test Party"
        private const val reputationString: String = "0"
    }

    @get:Rule
    val mActivityTestRule: ActivityTestRule<PartyActivity> = ActivityTestRule<PartyActivity>(PartyActivity::class.java)

    @Test
    fun testA_createSingleParty() {
        onView(withId(R.id.fab_parties)).perform(click())

        onView(withId(R.id.et_party_name)).perform(typeText(SinglePartyName))
        onView(withId(R.id.et_party_reputation)).perform(typeText(reputationString), closeSoftKeyboard())
        onView(withId(R.id.btn_save_party)).perform(click())

        onView(withId(R.id.partyRecycler)).check(matches(hasDescendant(withText(SinglePartyName))))
    }

    @Test
    fun testB_deleteSingleParty() {
        onView(withId(R.id.partyRecycler)).check(matches(hasDescendant(withText(SinglePartyName))))

        onView(withId(R.id.partyRecycler)).perform(actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(SinglePartyName)), swipeRight()))

        onView(allOf(withId(R.id.partyRecycler), not(withText(SinglePartyName))))
    }

    @Test
    fun testC_createManyParties() {
        for (index in 0..1) {
            onView(withId(R.id.fab_parties)).perform(click())

            onView(withId(R.id.et_party_name)).perform(typeText("$ManyPartiesName $index"))
            onView(withId(R.id.et_party_reputation)).perform(typeText(reputationString), closeSoftKeyboard())
            onView(withId(R.id.btn_save_party)).perform(click())

            onView(withId(R.id.partyRecycler)).check(matches(hasDescendant(withText("$ManyPartiesName $index"))))
        }
    }

    @Test
    fun testD_deleteManyParties() {

        for (index in 0..1) {
            onView(withId(R.id.partyRecycler)).check(matches(hasDescendant(withText("$ManyPartiesName $index"))))
        }

        onView(withId(R.id.action_delete)).perform(click())

        for (index in 0..1) {
            onView(allOf(withId(R.id.partyRecycler), not(withText("$ManyPartiesName $index"))))
        }
    }
}

