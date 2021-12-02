package com.dubizzle.classifiedapp

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.*
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ClassifiedUITest {

    private lateinit var device: UiDevice
    private val pkgName = "com.dubizzle.classifiedapp"
    private val launchTimeout = 5000L

    @Before
    fun startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from the home screen
        device.pressHome()

        // Wait for launcher
        val launcherPackage: String = device.launcherPackageName
        assertThat(launcherPackage, notNullValue())
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), launchTimeout)

        // Launch the app
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(
            pkgName).apply {
            // Clear out any previous instances
            this?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(pkgName).depth(0)), launchTimeout)
    }

    @Test
    @Throws(Exception::class)
    fun isRowItemPresent() {
        val list = UiScrollable(UiSelector().resourceId(viewId("recyclerviewClassified")))
        for (i in 0 until list.childCount) {
            //Index of list item
            val itemIndex = i + 1
            val uiObject = list.getChild(getRowItem(itemIndex, "nameClassified"))
            val uiObject2 = list.getChild(getRowItem(itemIndex, "priceClassified"))
            Assert.assertNotNull(uiObject.text)
            Assert.assertNotNull(uiObject2.text)
        }
    }


    @Test
    fun navigationUITest() {

        onView(withId(R.id.recyclerviewClassified)).perform(click())
        onView(withId(R.id.details_fragment)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.classified_list)).check(matches(isDisplayed()))
        // Detail fragment is not visible
        onView(withId(R.id.details_fragment)).check(doesNotExist())
    }


    private fun getRowItem(itemIndex: Int, resourceId: String?): UiSelector? {
        return UiSelector()
            .resourceId(resourceId?.let { getResourceId(it) })
            .enabled(true).instance(itemIndex)
    }

    private fun getResourceId(resourceId: String): String {
        return "$pkgName:id/$resourceId"
    }

    private fun viewId(id: String): String {
        return "$pkgName:id/$id"
    }
}
