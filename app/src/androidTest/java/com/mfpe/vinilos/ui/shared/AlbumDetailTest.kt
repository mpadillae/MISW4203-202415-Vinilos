package com.mfpe.vinilos.ui.shared


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mfpe.vinilos.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumDetailTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(UserSelectActivity::class.java)

    @Test
    fun albumDetailTest() {
        val button = onView(
            allOf(
                withId(R.id.button_collectors), withText("COLECCIONISTA"),
                childAtPosition(
                    allOf(
                        withId(R.id.main),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        button.perform(click())

        val cardView = onView(
            allOf(
                withId(R.id.album_card),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recycler_albums),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        sleep(5000)
        cardView.perform(click())

        val imageView = onView(
            allOf(
                withId(R.id.album_image_view),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        sleep(5000)
        imageView.check(matches(isDisplayed()))

        val imageView2 = onView(
            allOf(
                withId(R.id.artist_image_view),
                withParent(
                    allOf(
                        withId(R.id.artist_layout),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        sleep(5000)
        imageView2.check(matches(isDisplayed()))

        val tabView = onView(
            allOf(
                withContentDescription("Canciones"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tabLayout),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        sleep(5000)
        tabView.perform(click())

        val tabView2 = onView(
            allOf(
                withContentDescription("Comentarios"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tabLayout),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        sleep(5000)
        tabView2.perform(click())

        val tabView3 = onView(
            allOf(
                withContentDescription("Información"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tabLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        sleep(5000)
        tabView3.perform(click())

        val appCompatImageView = onView(
            allOf(
                withId(R.id.back_button), withContentDescription("Devolverse"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        sleep(5000)
        appCompatImageView.perform(click())

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
