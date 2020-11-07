package ch.zkb.registrierung.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ch.zkb.registrierung.data.model.RegisteredUser

@Database(entities = [RegisteredUser::class], version = 1, exportSchema = false)
abstract class ZkbDatabase : RoomDatabase() {

    abstract fun userDao(): RegisteredUserDao

    companion object {
        var INSTANCE: ZkbDatabase? = null

        fun getDatabase(context: Context): ZkbDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ZkbDatabase::class.java,
                    "zkb_db"
                ).build()
                INSTANCE = instance
                // return the instance
                instance
            }

        }
    }


}