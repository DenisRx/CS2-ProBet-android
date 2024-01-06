package com.denisrx.cs2probet.data

import android.content.Context
import com.denisrx.cs2probet.data.database.TeamDao
import com.denisrx.cs2probet.data.database.TeamEntity
import com.denisrx.cs2probet.data.database.asTeamEntityList
import com.denisrx.cs2probet.data.database.asTeamList
import com.denisrx.cs2probet.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository that provides insert, update, delete, and retrieve of [TeamEntity] from a given data source.
 */
interface TeamRepository {
    /**
     * Retrieve all the teams from the given data source.
     */
    fun getTeams(): Flow<List<Team>>

    /**
     * Clear table and save teams in the data source
     */
    suspend fun replaceTeams(teams: List<Team>)
}

class CachingTeamRepository(private val teamDao: TeamDao, context: Context) : TeamRepository {
    override fun getTeams(): Flow<List<Team>> = teamDao.getAll().map { it.asTeamList() }

    override suspend fun replaceTeams(teams: List<Team>) = teamDao.replaceAll(teams.asTeamEntityList())
}
