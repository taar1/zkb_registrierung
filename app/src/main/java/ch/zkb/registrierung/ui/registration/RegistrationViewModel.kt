package ch.zkb.registrierung.ui.registration

import android.app.Application
import android.util.Log
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

    private val registrationRepository: RegistrationRepository =
        RegistrationRepository(ZkbDatabase.getDatabase(app).userDao())

    private val _registrationForm = MutableLiveData<RegistrationFormState>()
    val registrationFormState: LiveData<RegistrationFormState> = _registrationForm

    private val _registrationResult = MutableLiveData<RegistrationResult>()
    val registrationResult: LiveData<RegistrationResult> = _registrationResult

    fun insertUserToDb(fullname: String, email: String, birthdate: Long) {

        viewModelScope.launch {

            // TODO FIXME insert into DB, check if success and update "registrationResult" object
            // TODO FIXME insert into DB, check if success and update "registrationResult" object
            // TODO FIXME insert into DB, check if success and update "registrationResult" object
            // TODO FIXME insert into DB, check if success and update "registrationResult" object

            val result =
                registrationRepository.insertUser(RegisteredUser(fullname, email, birthdate))

            registrationRepository.getUser(email)

//            _registrationResult.value =

//            try {
//
//
//                Log.d(TAG, "XXXXX refreshDataFromRepository: NETWORK OK")
//            } catch (networkError: IOException) {
//                Log.d(TAG, "XXXXX refreshDataFromRepository: IOException NO NETWORK")
//
//                repository.getSystemsListFromRoom()
//                _systems.postValue(repository.systemsList)
//            }
//
//            // If data is empty show a error message
//            if (repository.systemsList.isNullOrEmpty()) {
//                Log.d(TAG, "XXXXX refreshDataFromRepository: isNullOrEmpty")
//                _eventNetworkError.value = true
//            }
        }

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
        // TODO email validation
        // TODO email validation
        // TODO email validation
        // TODO email validation
        // TODO email validation
        return email.length > 5
    }


}