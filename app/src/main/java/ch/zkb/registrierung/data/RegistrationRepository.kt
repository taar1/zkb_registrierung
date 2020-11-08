package ch.zkb.registrierung.data

import ch.zkb.registrierung.data.model.RegisteredUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegistrationRepository(private val userDao: UserDao) {

    var user: RegisteredUser? = null
        private set

    val isRegistered: Boolean
        get() = user != null

    init {
        user = null
    }

    suspend fun insertUser(user: RegisteredUser) {
        withContext(Dispatchers.IO) {
            userDao.insert(user)

            setRegisteredUser(user)
        }
    }

//    fun register(fullname: String, email: String, birthdate: Long): Result<RegisteredUser> {
//        // handle registration
//
//        val result = dataSource.register(fullname, email, birthdate)
//
//        if (result is Result.Success) {
//            setRegisteredUser(result.data)
//        }
//
//        return result
//    }
//
    private fun setRegisteredUser(registeredUser: RegisteredUser) {
        this.user = registeredUser
    }
}