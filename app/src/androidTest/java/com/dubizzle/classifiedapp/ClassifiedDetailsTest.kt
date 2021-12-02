package com.dubizzle.classifiedapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dubizzle.classifiedapp.model.ClassifiedResults
import com.dubizzle.classifiedapp.utils.Constants
import com.dubizzle.classifiedapp.view.ClassifiedDetailsFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ClassifiedDetailsTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var scenario : FragmentScenario<ClassifiedDetailsFragment>
    private val detailsFragment = ClassifiedDetailsFragment()

    @Before
    fun init(){

        hiltRule.inject()

        val bundle = Bundle()
        val imgIds : List<String> = mutableListOf("9355183956e3445e89735d877b798689")
        val imgUrls : List<String> = mutableListOf("https://demo-app-photos-45687895456123.s3.amazonaws.com/9355183956e3445e89735d877b798689?AWSAccessKeyId=ASIASV3YI6A44DAZEYC4&Signature=i4Xspd1lP6RkV2QMVkYzIjsTSik%3D&x-amz-security-token=IQoJb3JpZ2luX2VjEB8aCXVzLWVhc3QtMSJHMEUCIQDKWhYQNqd0%2B5ykK27lHWmkDA9se2m91gqtwjW90QYl9gIgNR6rcS8g21cW4HLHyf21sk7QvhqkF3%2BmcAZkf37iYu0qnQII6P%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARADGgwxODQzOTg5NjY4NDEiDN2qtWCHLWvVR9UrRSrxAYXU4yWh2v84OwshCe3mf5347a1lsoj5NeQFjv%2FfSgNK%2BtK%2FG7E1HW8n3bzQ4ESdkegbRgV3plemiRjppnVHpkuKslCWwdOetJ0iE7hh8CMnewfpVnHWg9b2WWXW7nDKP8943QEbwBgr2nZjGk6cXwm800lZzs%2Bji2VEB4DOMbe1EvK9btwEaEyAQbZ8Q71ES%2BIlh8vvy1cSctIwHFZrpuIIXyldQhyhBrZ9Meiou1K0xX4%2Ba2LgKtT2Oj83ijzy8dNll%2BuJVELYgmVmcAVTF703XpcsXop7a0EljfuYRNPmvkJFGunk0EBARHvwTdTdOhIwo4%2BXjQY6mgHkkdeauB2FFiSJCbwXTbojPieXAYJC0xeye4afQ0VachdiOIwTR5DisCBb1jft%2Bzml7py5prkgutGqr72yKpvR6O83QC1GxawQDYKenz25WfXSuINxHLZkhPAHqyuvGFg2WtMCeEghoJOPubVZZsFW7%2BhYvZi5kNkRds7cvofBnMX1Kep3qxDSE79qSwme9e0UWwC4uHqAiYLL&Expires=1638260352")
        val imgUrlsthumbnail : List<String> = mutableListOf("")

        val data = ClassifiedResults("2021-12-01 04:04:17.566515", "AED 5", "Notebook",
            "4878bf592579410fba52941d00b62f94", imgIds, imgUrls, imgUrlsthumbnail)

        bundle.putParcelable(Constants.KEY_GLASSIFIED, data)

        scenario = launchFragmentInContainer(bundle, R.style.Theme_ClassifiedApp, Lifecycle.State.RESUMED, object: FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
               return detailsFragment
            }
        })
    }

    @Test
    fun informationDisplayed() {
        onView(ViewMatchers.withId(R.id.txtNameClassified))
            .check(ViewAssertions.matches(ViewMatchers.withText("Notebook")))
    }
}