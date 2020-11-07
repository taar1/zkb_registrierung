package ch.zkb.registrierung.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "registered_user")
data class RegisteredUser(
    val fullname: String,
    @PrimaryKey val email: String,
    val birthdate: Long
)