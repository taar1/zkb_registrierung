package ch.zkb.registrierung.ui.registration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ch.zkb.registrierung.data.RegistrationRepository
import ch.zkb.registrierung.data.ZkbDatabase
import ch.zkb.registrierung.data.model.RegisteredUser
import kotlinx.coroutines.launch

class RegistrationSuccessViewModel(app: Application) : AndroidViewModel(app) {

    private val TAG = "RegistrationSuccessViewModel"

    private val registrationRepository: RegistrationRepository =
        RegistrationRepository(ZkbDatabase.getDatabase(app).userDao())

    private val _registeredUser = MutableLiveData<RegisteredUser>()
    val registeredUser: LiveData<RegisteredUser> = _registeredUser

    fun getUser(email: String) {
        viewModelScope.launch {
            _registeredUser.value = registrationRepository.getUser(email)
        }
    }

}