package ch.zkb.registrierung.ui.registration

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistrationViewModelTest {

    @Test
    fun testEmailValidator() {
        val tasksViewModel = RegistrationViewModel(ApplicationProvider.getApplicationContext())
        Assert.assertTrue(tasksViewModel.isEmailValid("derbsland@gmail.com"))
    }
}
