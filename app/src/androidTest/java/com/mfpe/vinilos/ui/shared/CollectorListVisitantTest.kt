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
class CollectorListVisitantTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(UserSelectActivity::class.java)

    @Test
    fun collectorListVisitantTest() {
        val button = onView(
            allOf(
                withId(R.id.button_visitors), withText("VISITANTE"),
                childAtPosition(
                    allOf(
                        withId(R.id.main),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        button.perform(click())

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_collections), withContentDescription("Coleccionistas"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val linearLayout = onView(
            allOf(
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        sleep(3000)
        linearLayout.check(matches(isDisplayed()))
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