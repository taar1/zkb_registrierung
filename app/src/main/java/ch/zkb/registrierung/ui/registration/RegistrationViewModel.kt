package ch.zkb.registrierung.ui.registration

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ch.zkb.registrierung.R
import ch.zkb.registrierung.data.RegistrationRepository
import ch.zkb.registrierung.data.ZkbDatabase

class RegistrationViewModel(app: Application) : AndroidViewModel(app) {

    // TODO FIXME hier crasht es noch...
    // TODO FIXME hier crasht es noch...
    // TODO FIXME hier crasht es noch...
    // TODO FIXME hier crasht es noch...
    // TODO FIXME hier crasht es noch...
//    val userDao = ZkbDatabase.getDatabase(app).userDao()
//    private val registrationRepository: RegistrationRepository = RegistrationRepository(userDao)
    private val registrationRepository: RegistrationRepository =
        RegistrationRepository(ZkbDatabase.getDatabase(app).userDao())

    private val _registrationForm = MutableLiveData<RegistrationFormState>()
    val registrationFormState: LiveData<RegistrationFormState> = _registrationForm

    private val _registrationResult = MutableLiveData<RegistrationResult>()
    val registrationResult: LiveData<RegistrationResult> = _registrationResult

    fun register(fullname: String, email: String, birthdate: Long) {
        // can be launched in a separate asynchronous job


        // TODO FIXME insert stuff to room DB
        // TODO FIXME insert stuff to room DB
        // TODO FIXME insert stuff to room DB
        // TODO FIXME insert stuff to room DB

//        val result = registrationRepository.register(fullname, email, birthdate)
//
//        if (result is Result.Success) {
//            _registrationResult.value = RegistrationResult(success = RegisteredUserView(displayName = result.data.fullname))
//        } else {
//            _registrationResult.value = RegistrationResult(error = R.string.register_failed)
//        }
    }

    private val TAG = "RegistrationViewModel"

    fun registrationDataChanged(fullname: String, email: String, birthdate: Long) {
        Log.d(TAG, "registrationDataChanged: birthdate: " + birthdate)
        if (!isFullnameValid(fullname)) {
            _registrationForm.value =
                RegistrationFormState(fullnameError = R.string.invalid_fullname)
        } else if (!isEmailValid(email)) {
            _registrationForm.value = RegistrationFormState(emailError = R.string.invalid_email)
        } else if (birthdate < 1000L) {
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
        Log.d(TAG, "isEmailValid: " + email)
        return email.length > 5
    }


}