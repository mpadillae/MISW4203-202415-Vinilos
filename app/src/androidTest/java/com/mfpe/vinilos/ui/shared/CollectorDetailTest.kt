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
class CollectorDetailTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(UserSelectActivity::class.java)

    @Test
    fun collectorDetailTest() {
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
        sleep(5000)
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
        sleep(5000)
        bottomNavigationItemView.perform(click())

        val cardView = onView(
            allOf(
                withId(R.id.collectorCard),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recycler_collectors),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        sleep(5000)
        cardView.perform(click())

        val linearLayout = onView(
            allOf(
                withId(R.id.collector_layout_id),
                withParent(
                    allOf(
                        withId(R.id.main),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        sleep(5000)
        linearLayout.check(matches(isDisplayed()))

        val recyclerView = onView(
            allOf(
                withId(R.id.recycler_collector_favorite_performers),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        sleep(5000)
        recyclerView.check(matches(isDisplayed()))

        val tabView = onView(
            allOf(
                withContentDescription("Álbumes"),
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

        val viewGroup = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.recycler_collector_albums),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        sleep(5000)
        viewGroup.check(matches(isDisplayed()))

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

        val linearLayout2 = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.recycler_comments),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        sleep(5000)
        linearLayout2.check(matches(isDisplayed()))

        val appCompatImageView = onView(
            allOf(
                withId(R.id.back_button), withContentDescription("Devolverse"),
                childAtPosition(
                    allOf(
                        withId(R.id.main),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
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
