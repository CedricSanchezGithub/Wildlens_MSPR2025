package com.wildlens.mspr_2025.ui.screens.iascreen.state

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.wildlens.mspr_2025.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IAActivityTest {

    @Rule @JvmField
    val activityRule = ActivityTestRule(IAActivity::class.java)

    @Test
    fun test_etl_button_shows_success_message() {
        // Clique sur le bouton qui déclenche l'ETL
        onView(withId(R.id.button_etl)).perform(click())

        // Vérifie que le message de succès est affiché
        onView(withId(R.id.text_result))
            .check(matches(isDisplayed()))
            .check(matches(withText("ETL terminé")))
    }
}
