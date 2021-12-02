package com.dubizzle.classifiedapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.dubizzle.classifiedapp.view.ClassifiedDetailsFragment
import com.dubizzle.classifiedapp.view.ClassifiedListFragment
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
// @ExperimentalCoroutinesApi
@HiltAndroidTest
class ClassifiedListTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val scenario = launchFragmentInContainer<ClassifiedListFragment>()
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun classifiedContainerIsDisplayed() {
        onView(withId(R.id.recyclerviewClassified))
            .check(matches(isDisplayed()))
    }

    @Test(expected = PerformException::class)
    fun itemWithText_doesNotExist() {
        onView(withId(R.id.recyclerviewClassified))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("not in the list"))
                )
            )
    }

    @Test
    fun testNavigationToDetailsScreen() {
        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        // Create FragmentScenario for the details screen
        val titleScenario = launchFragmentInContainer<ClassifiedDetailsFragment>()

        titleScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.nav_graph)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // Verify that performing a click changes the NavController’s state
        onView(withId(R.id.recyclerviewClassified)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(4)).
            perform(click())

        assert(navController?.currentDestination?.id == R.id.classified_details_fragment)
    }
}