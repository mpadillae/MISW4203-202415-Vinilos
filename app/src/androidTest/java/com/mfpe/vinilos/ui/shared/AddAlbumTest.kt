package com.mfpe.vinilos.ui.shared

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mfpe.vinilos.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onData
import org.hamcrest.Matchers.`is`
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.lang.Thread.sleep


@LargeTest
@RunWith(AndroidJUnit4::class)
class AddAlbumTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(UserSelectActivity::class.java)

    @Test
    fun testAddAlbum() {

        onView(allOf(withId(R.id.button_collectors), withText("COLECCIONISTA"), isDisplayed()))
            .perform(click())

        onView(withId(R.id.add_album_button)).perform(click())

        onView(withId(R.id.et_name)).perform(replaceText("Nuevo Álbum"))

        onView(withId(R.id.et_url_cover)).perform(replaceText("https://upload.wikimedia.org/wikipedia/en/1/17/The_Killers_-_Hot_Fuss.png"))

        onView(withId(R.id.sp_genre)).perform(click())
        onData(allOf(`is`(String::class.java), `is`("Rock"))).perform(click())

        onView(withId(R.id.et_release_date)).perform(replaceText("12/11/2023"))

        onView(withId(R.id.sp_record_label)).perform(click())
        onData(allOf(`is`(String::class.java), `is`("Sony Music"))).perform(click())

        onView(withId(R.id.et_description)).perform(replaceText("Descripción del álbum de prueba"))

        onView(withId(R.id.btn_add_album)).perform(click())


        onView(withId(R.id.navigation_albums))
            .check(matches(isDisplayed()))

        val appCompatImageView2 = onView(
            allOf(
                withId(R.id.exitButton), withContentDescription("Salir al menú principal"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withId(R.id.container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        sleep(5000)
        appCompatImageView2.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}