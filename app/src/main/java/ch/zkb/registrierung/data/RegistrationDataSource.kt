package ch.zkb.registrierung.data

import ch.zkb.registrierung.data.model.RegisteredUser
import java.io.IOException
import java.util.*

/**
 * Class that handles registration and retrieves user information
 */
class RegistrationDataSource {

    fun register(fullname: String, email: String, birthdate: Long): Result<RegisteredUser> {
        try {
            val registeredUser = RegisteredUser(fullname, email, birthdate)
            return Result.Success(registeredUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error registering", e))
        }
    }

}