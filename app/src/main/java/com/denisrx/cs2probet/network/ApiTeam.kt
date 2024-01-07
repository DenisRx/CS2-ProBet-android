package com.denisrx.cs2probet.network

import com.denisrx.cs2probet.model.Team

/**
 * Object representing a [Team] and used as DTO
 */
data class ApiTeam(
    val points: Int,
    val place: Int,
    val team: ApiTeamInfo,
    val change: Int,
    val isNew: Boolean,
)

data class ApiTeamInfo(
    val name: String,
    val id: Int,
)

/**
 * Converts the response from the API to [Team] domain objects
 */
fun List<ApiTeam>.asDomainObjects(): List<Team> {
    return this.map {
        Team(
            points = it.points,
            place = it.place,
            name = it.team.name,
            id = it.team.id,
            change = it.change,
            isNew = it.isNew,
            isSelected = false,
        )
    }
}
