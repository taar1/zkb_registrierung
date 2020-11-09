package ch.zkb.registrierung.ui.registration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ch.zkb.registrierung.R
import ch.zkb.registrierung.data.RegistrationRepository
import ch.zkb.registrierung.data.ZkbDatabase
import ch.zkb.registrierung.data.model.RegisteredUser
import kotlinx.coroutines.launch

class RegistrationViewModel(app: Application) : AndroidViewModel(app) {
    private val TAG = "RegistrationViewModel"

    private val registrationRepository: RegistrationRepository =
        RegistrationRepository(ZkbDatabase.getDatabase(app).userDao())

    private val _registrationForm = MutableLiveData<RegistrationFormState>()
    val registrationFormState: LiveData<RegistrationFormState> = _registrationForm

    private val _registrationResult = MutableLiveData<RegistrationResult>()
    val registrationResult: LiveData<RegistrationResult> = _registrationResult


    fun insertUserToDb(fullname: String, email: String, birthdate: Long) {

        // Using Coroutines so we don't block the UI.
        viewModelScope.launch {

            registrationRepository.insertUser(RegisteredUser(fullname, email, birthdate))
            val registeredUser: RegisteredUser? = registrationRepository.getUser(email)

            if (registeredUser == null) {
                _registrationResult.value = RegistrationResult(error = R.string.register_failed)
            } else {
                _registrationResult.value = RegistrationResult(success = registeredUser)
            }
        }
    }

    fun registrationDataChanged(fullname: String, email: String, birthdate: Long) {
        if (!isFullnameValid(fullname)) {
            _registrationForm.value =
                RegistrationFormState(fullnameError = R.string.invalid_fullname)
        } else if (!isEmailValid(email)) {
            _registrationForm.value = RegistrationFormState(emailError = R.string.invalid_email)
        } else if (birthdate == 0L) {
            // birthdate == 0L check is not ideal.
            // But since we only select a day and no time the value 0L will never occur unless the field is empty.
            _registrationForm.value =
                RegistrationFormState(birthdateError = R.string.invalid_birthdate)
        } else {
            _registrationForm.value = RegistrationFormState()
        }
    }

    /**
     * Validating the entered name here
     */
    private fun isFullnameValid(fullname: String): Boolean {
        return fullname.isNotBlank()
    }

    /**
     * Validating the entered email address here
     */
    private fun isEmailValid(email: String): Boolean {
        // TODO email validation
        // TODO email validation
        // TODO email validation
        // TODO email validation
        // TODO email validation
        return email.length > 5
    }


}