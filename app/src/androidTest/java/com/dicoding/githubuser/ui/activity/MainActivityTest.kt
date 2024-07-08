package com.dicoding.githubuser.ui.activity

import android.view.KeyEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.githubuser.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    private val dummy = "Lowl16"

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testSearchUser(){
        Espresso.onView(withId(R.id.searchBar)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.searchView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withHint(R.string.search_view_hint)).perform(ViewActions.typeText(dummy)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER))

        Thread.sleep(1000)

        Espresso.onView(withId(R.id.rvUser)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rvUser)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                ViewActions.click()
            ))
        Espresso.onView(withId(R.id.tvUser))
            .check(ViewAssertions.matches(ViewMatchers.withText(dummy)))
    }
}