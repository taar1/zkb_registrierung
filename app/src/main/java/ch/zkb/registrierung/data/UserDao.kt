package ch.zkb.registrierung.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ch.zkb.registrierung.data.model.RegisteredUser

@Dao
interface UserDao {

    @Query("SELECT * FROM registered_user ORDER BY fullname ASC")
    fun getUsers(): List<RegisteredUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(registeredUser: RegisteredUser)

    @Query("DELETE FROM registered_user")
    suspend fun deleteAll()
}