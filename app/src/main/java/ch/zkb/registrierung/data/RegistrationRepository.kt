package ch.zkb.registrierung.data

import ch.zkb.registrierung.data.model.RegisteredUser

class RegistrationRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: RegisteredUser) {
        userDao.insert(user)
    }

    suspend fun getUser(email: String): RegisteredUser {
        return userDao.getUser(email)
    }

}