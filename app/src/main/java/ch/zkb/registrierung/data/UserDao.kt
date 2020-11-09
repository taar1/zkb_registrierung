package ch.zkb.registrierung.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ch.zkb.registrierung.data.model.RegisteredUser

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(registeredUser: RegisteredUser)

    @Query("DELETE FROM registered_user")
    suspend fun deleteAll()

    @Query("SELECT * FROM registered_user where email = :email")
    suspend fun getUser(email: String): RegisteredUser
}