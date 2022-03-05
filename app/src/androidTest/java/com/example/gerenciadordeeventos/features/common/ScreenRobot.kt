package com.example.cleanmvvmapp.test.features.common

import android.app.Activity
import android.content.Context
import android.support.test.InstrumentationRegistry
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import com.example.cleanmvvmapp.R
import com.example.cleanmvvmapp.presenter.adapter.CarrosAdapter
import com.example.cleanmvvmapp.test.features.common.actions.CallOnClickAction.callOnClick
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.Is.`is`
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class ScreenRobot {

    private val activityContext: Activity? = null

    val context: Context
        get() = InstrumentationRegistry.getTargetContext()

    @Throws(InterruptedException::class)
    @JvmOverloads
    fun sleep(seconds: Int = 1) {
        Thread.sleep(seconds * 1000L)
    }

    fun checkIsDisplayed(@IdRes vararg viewIds: Int) {
        for (viewId in viewIds) {
            onView(withId(viewId)).check(matches(isDisplayed()))
        }
    }

    fun checkIsClickable(@IdRes vararg viewIds: Int) {
        for (viewId in viewIds) {
            onView(withId(viewId)).check(matches(isClickable()))
        }
    }

    fun viewIsDisplayedAfterProgressDialogIsGone(viewId: Int) {
        onView(withId(viewId))
            .inRoot(not(isDialog()))
            .check(matches(isDisplayed()))
    }

    fun checkIsHidden(@IdRes vararg viewIds: Int) {
        for (viewId in viewIds) {
            onView(withId(viewId)).check(matches(not(isDisplayed())))
        }
    }

    fun checkDoesNotExist(@IdRes vararg viewIds: Int) {
        for (viewId in viewIds) {
            onView(withId(viewId)).check(doesNotExist())
        }
    }

    fun checkViewHasText(@IdRes viewId: Int, @StringRes messageResId: Int) {
        onView(withId(viewId)).check(matches(withText(messageResId)))
    }

    fun checkViewHasDrawableAndTag(imageResId: Int) {
        onView(withTagValue(`is`(imageResId as Any))).check(matches(isDisplayed()))
    }

    fun scrollViewDown(@IdRes viewIds: Int) {
        onView(withId(viewIds)).perform(swipeUp(), click())
    }

    fun checkViewHasText(@IdRes viewId: Int, expected: String) {
        onView(withId(viewId)).check(matches(withText(expected)))
    }

    fun scrollViewUp(@IdRes viewIds: Int) {
        onView(withId(viewIds)).perform(swipeDown(), click())
    }

    fun checkViewContainsText(text: String) {
        onView(withText(text)).check(matches(isDisplayed()))
    }

    fun checkViewHasHint(@IdRes viewId: Int, @StringRes messageResId: String) {
        onView(withId(viewId)).check(matches(withHint(messageResId)))
    }

    fun callOnClickOnView(@IdRes viewId: Int) {
        // On small Views, click action isn't always detected.
        // To avoid this, use callOnClick().
        onView(withId(viewId)).perform(callOnClick())
    }

    fun clickOnView(@IdRes viewId: Int) {
        onView(withId(viewId)).perform(click())
    }

    fun pressBack() {
        Espresso.pressBack()
    }

    fun goBackFromToolbar() {
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    fun closeKeyboard() {
        Espresso.closeSoftKeyboard()
    }

    fun pressImeAction(@IdRes viewId: Int) {
        onView(withId(viewId)).perform(pressImeActionButton())
    }

    fun assertItTakeMeToScreen(targetClass: Class<*>) {
        intended(hasComponent(targetClass.name))
    }

    fun enterTextIntoView(@IdRes viewId: Int, text: String) {
        onView(withId(viewId)).perform(typeText(text))
        closeKeyboard()
    }

    fun checkDialogWithTextIsDisplayed(@StringRes messageResId: Int) {
        onView(withText(messageResId))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    fun checkDialogWithTextIsDisplayed(message: String) {
        onView(withText(message))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    fun swipeLeftOnView(@IdRes viewId: Int) {
        onView(withId(viewId)).perform(swipeLeft())
    }

    fun swipeRightOnView(@IdRes viewId: Int) {
        onView(withId(viewId)).perform(swipeRight())
    }

    fun clickOnCardForList(@IdRes viewId: Int, position: Int) {
        onView(withIndex(withId(viewId), position)).perform(click())
    }

    fun waitForIdToAppear(viewId: Int, seconds: Long) {
        onView(isRoot()).perform(waitId(viewId, TimeUnit.SECONDS.toMillis(seconds)))
    }

    private fun waitId(viewId: Int, seconds: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for a specific view with id <$viewId> during $seconds millis."
            }

            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadUntilIdle()
                val startTime = System.currentTimeMillis()
                val endTime = startTime + seconds
                val viewMatcher = withId(viewId)
                do {
                    for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            if(child.visibility == View.VISIBLE) {
                                return
                            }
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


    private fun withIndex(matcher: Matcher<View>, index: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            internal var currentIndex = 0

            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                matcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                return matcher.matches(view) && currentIndex++ == index
            }
        }
    }

    fun clickOnItemList(recycler: Int, posCarro: Int) {
        onView(withId(recycler))
            .perform(scrollToPosition<CarrosAdapter.CarrosViewHolder>(posCarro))

        onView(withId(recycler))
            .perform(actionOnItemAtPosition<CarrosAdapter.CarrosViewHolder>(posCarro,
            click()))

    }
}

