package com.example.githubapi.ui.main


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.githubapi.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest2() {
        val appCompatEditText = onView(
            allOf(
                withId(R.id.main_search_edit_text), withText("sample"),
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
        appCompatEditText.perform(replaceText("coway"))

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.main_search_edit_text), withText("coway"),
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
        appCompatEditText2.perform(closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.main_search_btn), withText("Search"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.base_recyclerView),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )

        onView(isRoot()).perform(waitId(R.id.main_item_repo_name, TimeUnit.SECONDS.toMillis(3)));
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        pressBack()

        val materialCheckBox = onView(
            allOf(
                withId(R.id.main_item_check_bookmark),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.base_recyclerView),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialCheckBox.perform(click())

        val materialCheckBox2 = onView(
            allOf(
                withId(R.id.main_item_check_bookmark),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.base_recyclerView),
                        1
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialCheckBox2.perform(click())

        val materialCheckBox3 = onView(
            allOf(
                withId(R.id.main_item_check_bookmark),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.base_recyclerView),
                        2
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialCheckBox3.perform(click())

        val recyclerView2 = onView(
            allOf(
                withId(R.id.base_recyclerView),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(2, click()))

        pressBack()

        val recyclerView3 = onView(
            allOf(
                withId(R.id.base_recyclerView),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView3.perform(actionOnItemAtPosition<ViewHolder>(1, click()))

        pressBack()

        val tabView = onView(
            allOf(
                withContentDescription("Bookmark"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_tab_layout),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        tabView.perform(click())

        val tabView2 = onView(
            allOf(
                withContentDescription("Search Result"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_tab_layout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        tabView2.perform(click())
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

    fun waitId(viewId: Int, millis: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for a specific view with id <$viewId> during $millis millis."
            }

            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadUntilIdle()
                val startTime = System.currentTimeMillis()
                val endTime = startTime + millis
                val viewMatcher = withId(viewId)
                do {
                    for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return
                        }
                    }
                    uiController.loopMainThreadForAtLeast(50)
                } while (System.currentTimeMillis() < endTime)
                throw PerformException.Builder()
                    .withActionDescription(this.description)
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(TimeoutException())
                    .build()
            }
        }
    }

}
