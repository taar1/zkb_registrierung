package ch.zkb.registrierung.ui.registration

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import ch.zkb.registrierung.R
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RegistrationActivityTest {

    /**
     * Please disable animations on your phone before running those tests.
     */
    val FORM_INPUT_NAME = "Dominik"
    val FORM_INPUT_EMAIL = "derbsland@gmail.com"

    val FORM_INPUT_DAY = 27
    val FORM_INPUT_MONTH = 6
    val FORM_INPUT_YEAR = 2015

    // Depending on your phone's locale this string date has to be adjusted
    val FORM_INPUT_BIRTHDATE = "27. June 2015"

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("ch.zkb.registrierung", appContext.packageName)
    }

    @Test
    fun testIsActivityInView() {
        ActivityScenario.launch(RegistrationActivity::class.java)

        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun testIsTextInput() {
        ActivityScenario.launch(RegistrationActivity::class.java)

        // Fill the form
        onView(withId(R.id.fullName)).perform(typeText(FORM_INPUT_NAME))
        onView(withId(R.id.email)).perform(typeText(FORM_INPUT_EMAIL))

        onView(withId(R.id.birthdate)).perform(click())
        onView(withId(R.id.datePickerLayout)).check(matches(isDisplayed()))

        onView(withId(R.id.datePicker)).perform(
            PickerActions.setDate(
                FORM_INPUT_YEAR,
                FORM_INPUT_MONTH,
                FORM_INPUT_DAY
            )
        )
        onView(withId(R.id.buttonConfirmBirthdate)).perform(click())

        // Check input values
        onView(withId(R.id.fullName)).check(matches(withText(FORM_INPUT_NAME)))
        onView(withId(R.id.email)).check(matches(withText(FORM_INPUT_EMAIL)))
        onView(withId(R.id.birthdate)).check(matches(withText(FORM_INPUT_BIRTHDATE)))

        onView(withId(R.id.register)).perform(click())

        // Now on SUCCESS view
        onView(withId(R.id.thankyou)).check(matches(isDisplayed()))
        onView(withId(R.id.value_name)).check(matches(withText(FORM_INPUT_NAME)))
        onView(withId(R.id.value_email)).check(matches(withText(FORM_INPUT_EMAIL)))
        onView(withId(R.id.value_birthdate)).check(matches(withText(FORM_INPUT_BIRTHDATE)))
1
    }


}