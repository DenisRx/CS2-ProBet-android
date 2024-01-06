package com.denisrx.cs2probet.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [TeamEntity::class], version = 1, exportSchema = false)
abstract class TeamDatabase : RoomDatabase() {
    abstract fun teamDao(): TeamDao

    companion object {
        @Volatile
        private var Instance: TeamDatabase? = null

        fun getDatabase(context: Context): TeamDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TeamDatabase::class.java, "team_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}