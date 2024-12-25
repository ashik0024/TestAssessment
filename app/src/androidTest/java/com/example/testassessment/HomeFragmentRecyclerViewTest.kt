package com.example.testassessment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.internal.matchers.TypeSafeMatcher
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentRecyclerViewTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewDisplayedAndPopulated() {
        // Initialize the idling resource
        var idlingResource: RecyclerViewIdlingResource? = null

        activityRule.scenario.onActivity { activity ->
            val recyclerView = activity.findViewById<RecyclerView>(R.id.photoRec)
            idlingResource = RecyclerViewIdlingResource(recyclerView)
        }

        // Register the idling resource (outside the main thread)
        idlingResource?.let {
            IdlingRegistry.getInstance().register(it)
        }

        try {
            /**
             * Test RecyclerView is visible
             */
            onView(withId(R.id.photoRec))
                .check(matches(isDisplayed()))

            /**
             * Test if the RecyclerView is displayed
             */
            onView(withId(R.id.photoRec))
                .check(matches(isDisplayed()))

            /**
             * Test scrolling to the first position and check if the item contains specific text
             */
            onView(withId(R.id.photoRec))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
            onView(withRecyclerView(R.id.photoRec).atPosition(0))
                .check(matches(hasDescendant(withText("accusamus beatae ad facilis cum similique qui sunt"))))

            onView(withRecyclerView(R.id.photoRec).atPosition(0))
                .check(matches(hasDescendant(withText("quidem molestiae enim"))))

            onView(withRecyclerView(R.id.photoRec).atPosition(0))
                .check(matches(hasDescendant(withText("Bret"))))

            /**
             * Test if the ImageView in the first item is displayed
             */
            onView(withRecyclerView(R.id.photoRec).atPositionOnView(0, R.id.thumbnailImage))
                .check(matches(isDisplayed()))
        } finally {
            // Unregister the idling resource
            idlingResource?.let {
                IdlingRegistry.getInstance().unregister(it)
            }
        }
    }


    // Helper function for RecyclerView matching
    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    // RecyclerViewMatcher implementation
    class RecyclerViewMatcher(private val recyclerViewId: Int) {

        fun atPosition(position: Int): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description) {
                    description.appendText("has item at position $position in RecyclerView with id $recyclerViewId")
                }

                override fun matchesSafely(view: View): Boolean {
                    val recyclerView = view.rootView.findViewById<RecyclerView>(recyclerViewId)
                    val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                    return view == viewHolder?.itemView
                }
            }
        }

        fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description) {
                    description.appendText("has view with id $targetViewId at position $position in RecyclerView with id $recyclerViewId")
                }

                override fun matchesSafely(view: View): Boolean {
                    val recyclerView = view.rootView.findViewById<RecyclerView>(recyclerViewId)
                    val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                        ?: return false
                    val targetView = viewHolder.itemView.findViewById<View>(targetViewId)
                    return view == targetView
                }
            }
        }

}
}
