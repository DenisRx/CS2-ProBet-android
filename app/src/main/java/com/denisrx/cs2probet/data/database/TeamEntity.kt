package com.denisrx.cs2probet.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.denisrx.cs2probet.model.Team

/**
 * Representation of the team object saved in the local database
 */
@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey
    val id: Int,
    val points: Int,
    val place: Int,
    val name: String,
    val change: Int,
    val isNew: Boolean,
    val isSelected: Boolean,
)

/**
 * Convert a [Team] to a [TeamEntity]
 * @return The converted [TeamEntity]
 */
fun Team.asTeamEntity(): TeamEntity = TeamEntity(
    id = this.id,
    points = this.points,
    place = this.place,
    name = this.name,
    change = this.change,
    isNew = this.isNew,
    isSelected = this.isSelected,
)

/**
 * Convert list of [Team] to a list of [TeamEntity]
 * @return The converted [TeamEntity] list
 */
fun List<Team>.asTeamEntityList(): List<TeamEntity> = this.map { it.asTeamEntity() }


/**
 * Convert [TeamEntity] to [Team]
 * @return The converted [Team]
 */
fun TeamEntity.asTeam(): Team = Team(
    id = this.id,
    points = this.points,
    place = this.place,
    name = this.name,
    change = this.change,
    isNew = this.isNew,
    isSelected = this.isSelected,
)

/**
 * Convert list of [TeamEntity] to a list of [Team]
 * @return The converted [Team] list
 */
fun List<TeamEntity>.asTeamList(): List<Team> = this.map { it.asTeam() }
