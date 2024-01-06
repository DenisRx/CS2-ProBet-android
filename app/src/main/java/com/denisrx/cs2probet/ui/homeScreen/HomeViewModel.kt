package com.denisrx.cs2probet.ui.homeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisrx.cs2probet.data.LeaderboardSampler
import com.denisrx.cs2probet.data.TeamRepository
import com.denisrx.cs2probet.model.Team
import com.denisrx.cs2probet.network.LeaderboardApi
import com.denisrx.cs2probet.network.asDomainObjects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(private val teamsRepository: TeamRepository) : ViewModel() {
    private val maxSelectableTeams = 3
    private val _uiState = MutableStateFlow(HomeUiState(LeaderboardSampler.leaderboard))

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    var leaderboardApiState: LeaderboardApiState by mutableStateOf(LeaderboardApiState.Loading)
        private set

    init {
        loadLeaderboard()
    }

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

    private fun fetchLeaderboard() {
        viewModelScope.launch {
            leaderboardApiState = try {
                val leaderboardResult = LeaderboardApi.retrofitService.getLeaderboard()
                _uiState.update { currentState ->
                    currentState.copy(leaderboard = leaderboardResult.asDomainObjects())
                }
                LeaderboardApiState.Success(leaderboardResult.asDomainObjects())
            } catch (e: IOException) {
                println("Failed to fetch leaderboard: $e")
                LeaderboardApiState.Error
            }
        }
    }

    private fun loadLeaderboard() {
        viewModelScope.launch {
            teamsRepository.getTeams().collect { savedLeaderboard ->
                if (savedLeaderboard.isNotEmpty()) {
                    _uiState.update { currentState ->
                        currentState.copy(leaderboard = savedLeaderboard)
                    }
                    leaderboardApiState = LeaderboardApiState.Success(_uiState.value.leaderboard)
                }
            }
        }
    }

    private fun saveLeaderboard() {
        viewModelScope.launch {
            teamsRepository.replaceTeams(_uiState.value.leaderboard)
        }
    }
}
