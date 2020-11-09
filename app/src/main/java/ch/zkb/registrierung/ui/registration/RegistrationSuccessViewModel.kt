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

class RegistrationSuccessViewModel(app: Application) : AndroidViewModel(app) {

    private val TAG = "RegistrationSuccessViewModel"

    private val registrationRepository: RegistrationRepository =
        RegistrationRepository(ZkbDatabase.getDatabase(app).userDao())

    private val _registrationResult = MutableLiveData<RegistrationResult>()
    val registrationResult: LiveData<RegistrationResult> = _registrationResult

    fun getUser(email: String) {
        viewModelScope.launch {
            val registeredUser: RegisteredUser? = registrationRepository.getUser(email)

            if (registeredUser == null) {
                _registrationResult.value = RegistrationResult(error = R.string.error_getting_user)
            } else {
                _registrationResult.value = RegistrationResult(success = registeredUser)
            }
        }
    }

}