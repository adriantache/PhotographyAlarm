package com.adriantache.photographyalarm.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adriantache.photographyalarm.model.SunriseInfo
import com.adriantache.photographyalarm.model.WeatherInfo
import com.adriantache.photographyalarm.model.WeatherInfoPoint

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(
    entities = [SunriseInfo::class, WeatherInfo::class, WeatherInfoPoint::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun dao(): AppDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java,
                    "app_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}
