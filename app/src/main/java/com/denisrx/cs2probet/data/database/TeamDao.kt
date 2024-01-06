package com.denisrx.cs2probet.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(teams: List<TeamEntity>)

    @Query("SELECT * from teams ORDER BY place ASC")
    fun getAll(): Flow<List<TeamEntity>>

    @Query("DELETE FROM teams")
    fun deleteAll()
}
