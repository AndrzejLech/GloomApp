package com.gloom.gloomapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.gloom.gloomapp.View.PartyActivity
import org.hamcrest.Matchers
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PlayersViewTest {
    companion object {
        private const val partyName: String = "Player Test Party"
        private const val reputationString: String = "0"
        private const val deleteSinglePlayerPLayerName: String = "Delete Single Player Test Player"
        private const val deleteManyPlayersPLayerName: String = "Delete Many Player Test Player"
    }

    @get:Rule
    val mActivityTestRule: ActivityTestRule<PartyActivity> = ActivityTestRule<PartyActivity>(PartyActivity::class.java)

    @Test
    fun A_createSinglePlayer(){
        onView(withId(R.id.fab_parties)).perform(click())

        onView(withId(R.id.et_party_name)).perform(typeText(partyName))
        onView(withId(R.id.et_party_reputation)).perform(typeText(reputationString), closeSoftKeyboard())
        onView(withId(R.id.btn_save_party)).perform(click())

        onView(withId(R.id.partyRecycler)).perform(actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(partyName)), click()))

        onView(withId(R.id.fab_players)).perform(click())
        onView(withId(R.id.et_name)).perform(typeText(deleteSinglePlayerPLayerName), closeSoftKeyboard())
        onView(withId(R.id.btn_save_player)).perform(click())

        onView(withId(R.id.playersRecycler)).check(matches(hasDescendant(withText(deleteSinglePlayerPLayerName))))
    }

    @Test
    fun B_deleteSinglePlayer() {
        onView(withId(R.id.partyRecycler)).perform(actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(partyName)), click()))

        onView(withId(R.id.playersRecycler)).perform(actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(deleteSinglePlayerPLayerName)), swipeRight()))

        onView(Matchers.allOf(withId(R.id.playersRecycler), Matchers.not(withText(deleteManyPlayersPLayerName))))
    }

    @Test
    fun C_createManyPlayers() {
        onView(withId(R.id.partyRecycler)).perform(actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(partyName)), click()))

        for (index in 0..1) {
            onView(withId(R.id.fab_players)).perform(click())
            onView(withId(R.id.et_name)).perform(typeText("$deleteManyPlayersPLayerName $index"), closeSoftKeyboard())
            onView(withId(R.id.btn_save_player)).perform(click())

            onView(withId(R.id.playersRecycler)).check(matches(hasDescendant(withText("$deleteManyPlayersPLayerName $index"))))
        }
    }

    @Test

    fun D_deleteManyPlayers() {
        onView(withId(R.id.partyRecycler)).perform(actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(partyName)), click()))

        onView(withId(R.id.action_delete))

        for (index in 0..1) {
            onView(Matchers.allOf(withId(R.id.playersRecycler), Matchers.not(withText("$deleteManyPlayersPLayerName $index"))))
        }

        Espresso.pressBack()
        onView(withId(R.id.partyRecycler)).perform(actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(partyName)), swipeRight()))
    }
}
