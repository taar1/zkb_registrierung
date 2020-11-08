package ch.zkb.registrierung.ui.registration

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import ch.zkb.registrierung.data.RegistrationRepository
import ch.zkb.registrierung.data.Result

import ch.zkb.registrierung.R
import ch.zkb.registrierung.data.ZkbDatabase
import java.util.*

class RegistrationViewModel(app: Application) : AndroidViewModel(app) {

    // TODO FIXME hier crasht es noch...
    // TODO FIXME hier crasht es noch...
    // TODO FIXME hier crasht es noch...
    // TODO FIXME hier crasht es noch...
    // TODO FIXME hier crasht es noch...
//    val userDao = ZkbDatabase.getDatabase(app).userDao()
//    private val registrationRepository: RegistrationRepository = RegistrationRepository(userDao)
    private val registrationRepository: RegistrationRepository = RegistrationRepository(ZkbDatabase.getDatabase(app).userDao())

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

    fun registrationDataChanged(fullname: String, email: String, birthdate: Long) {
        if (!isFullnameValid(fullname)) {
            _registrationForm.value = RegistrationFormState(fullnameError = R.string.invalid_fullname)
        } else if (!isEmailValid(email)) {
            _registrationForm.value = RegistrationFormState(emailError = R.string.invalid_email)
        } else {
            _registrationForm.value = RegistrationFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    // TODO namen validierung
    // TODO namen validierung
    // TODO namen validierung
    // TODO namen validierung
    // TODO namen validierung
    private fun isFullnameValid(fullname: String): Boolean {
        return if (fullname.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(fullname).matches()
        } else {
            fullname.isNotBlank()
        }
    }

    // A placeholder password validation check
    // TODO email validierung
    // TODO email validierung
    // TODO email validierung
    // TODO email validierung
    private fun isEmailValid(email: String): Boolean {
        return email.length > 5
    }


}