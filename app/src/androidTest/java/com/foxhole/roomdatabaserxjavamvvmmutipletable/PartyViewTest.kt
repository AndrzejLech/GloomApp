package com.foxhole.roomdatabaserxjavamvvmmutipletable

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.foxhole.roomdatabaserxjavamvvmmutipletable.View.PartyActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test

@LargeTest
class PartyViewTest {

    companion object {
        private const val deleteSinglePartyName: String = "Delete Single Test Party"
        private const val deleteManyPartiesName: String = "Delete Many Test Party"
        private const val reputationString: String = "0"
    }

    @get:Rule
    val mActivityTestRule: ActivityTestRule<PartyActivity> = ActivityTestRule<PartyActivity>(PartyActivity::class.java)

    @Test
    fun deleteSingleParty() {
        onView(withId(R.id.fab_parties)).perform(click())

        onView(withId(R.id.et_party_name)).perform(typeText(deleteSinglePartyName))
        onView(withId(R.id.et_party_reputation)).perform(typeText(reputationString), closeSoftKeyboard())
        onView(withId(R.id.btn_save_party)).perform(click())

        onView(withId(R.id.partyRecycler)).check(matches(hasDescendant(withText(deleteSinglePartyName))))

        onView(withId(R.id.partyRecycler)).perform(actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(deleteSinglePartyName)), swipeRight()))

        onView(allOf(withId(R.id.partyRecycler), not(withText(deleteSinglePartyName))))
    }

    @Test
    fun deleteManyParties() {

        for (index in 0..1) {
            onView(withId(R.id.fab_parties)).perform(click())

            onView(withId(R.id.et_party_name)).perform(typeText("$deleteManyPartiesName $index"))
            onView(withId(R.id.et_party_reputation)).perform(typeText(reputationString), closeSoftKeyboard())
            onView(withId(R.id.btn_save_party)).perform(click())

            onView(withId(R.id.partyRecycler)).check(matches(hasDescendant(withText("$deleteManyPartiesName $index"))))
        }

        onView(withId(R.id.action_delete)).perform(click())

        for(index in 0..1){
            onView(allOf(withId(R.id.partyRecycler), not(withText("$deleteManyPartiesName $index"))))
        }
    }

}

