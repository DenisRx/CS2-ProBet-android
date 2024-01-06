package com.denisrx.cs2probet.data

import android.content.Context
import com.denisrx.cs2probet.data.database.TeamDatabase

interface AppContainer {
    val teamRepository: TeamRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    override val teamRepository: TeamRepository by lazy {
        CachingTeamRepository(TeamDatabase.getDatabase(context = context).teamDao(), context)
    }
}
