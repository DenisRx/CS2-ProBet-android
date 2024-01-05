package com.denisrx.cs2probet.network

import com.denisrx.cs2probet.model.Team
import kotlinx.serialization.Serializable

@Serializable
data class ApiTeam(
    val points: Int,
    val place: Int,
    val teamInfo: ApiTeamInfo,
    val change: Int,
    val isNew: Boolean,
)

data class ApiTeamInfo(
    val name: String,
    val id: Int,
)

fun List<ApiTeam>.asDomainObjects(): List<Team> {
    return this.map {
        Team(
            points = it.points,
            place = it.place,
            name = it.teamInfo.name,
            id = it.teamInfo.id,
            change = it.change,
            isNew = it.isNew,
            isSelected = false,
        )
    }
}
