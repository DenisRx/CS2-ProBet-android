package com.denisrx.cs2probet.ui.homeScreen

import androidx.lifecycle.ViewModel
import com.denisrx.cs2probet.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val leaderboardData = listOf(
        Team(points = 980, place = 1, name = "Vitality", id = 9565, change = 0, isNew = false, isSelected = false),
        Team(points = 940, place = 2, name = "FaZe", id = 6667, change = 0, isNew = false, isSelected = false),
        Team(points = 519, place = 3, name = "MOUZ", id = 4494, change = 0, isNew = false, isSelected = false),
        Team(points = 404, place = 4, name = "Natus Vincere", id = 4608, change = 0, isNew = false, isSelected = false),
        Team(points = 365, place = 5, name = "Virtus.pro", id = 5378, change = 0, isNew = false, isSelected = false),
        Team(points = 344, place = 6, name = "Monte", id = 11811, change = 1, isNew = false, isSelected = false),
        Team(points = 342, place = 7, name = "G2", id = 5995, change = -1, isNew = false, isSelected = false),
        Team(points = 317, place = 8, name = "Complexity", id = 5005, change = 0, isNew = false, isSelected = false),
        Team(points = 291, place = 9, name = "Cloud9", id = 5752, change = 0, isNew = false, isSelected = false),
        Team(points = 288, place = 10, name = "Falcons", id = 11283, change = 0, isNew = false, isSelected = false),
    )

    private val maxSelectableTeams = 3
    private val _uiState = MutableStateFlow(HomeUiState(leaderboardData))

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun toggleSelectedTeam(teamId: Int): Boolean? {
        var newSelectionState: Boolean? = null

        _uiState.update {  currentState ->
            val updatedLeaderboard = currentState.leaderboard.map { team ->
                if (team.id == teamId) {
                    // Handle max selections
                    if (!team.isSelected && getSelectedTeams().count() == maxSelectableTeams)
                        return null

                    newSelectionState = !team.isSelected
                    team.copy(isSelected = newSelectionState!!)
                } else {
                    team
                }
            }
            currentState.copy(leaderboard = updatedLeaderboard)
        }

        return newSelectionState
    }

    fun editSelection() {
        _uiState.update { currentState ->
            currentState.copy(isEditing = true)
        }
    }

    fun confirmSelection() {
        _uiState.update { currentState ->
            currentState.copy(isEditing = false)
        }
    }

    private fun getSelectedTeams(): List<Team> {
        return _uiState.value.leaderboard.filter { it.isSelected }
    }
}
