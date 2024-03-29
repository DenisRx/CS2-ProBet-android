package com.denisrx.cs2probet.ui.homeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisrx.cs2probet.data.TeamRepository
import com.denisrx.cs2probet.data.UserPreferencesRepository
import com.denisrx.cs2probet.model.Team
import com.denisrx.cs2probet.network.LeaderboardApi
import com.denisrx.cs2probet.network.asDomainObjects
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.math.abs

class HomeViewModel(
    private val teamsRepository: TeamRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    private val maxSelectableTeams = 3
    private val correctPredictionPoints = 15
    private val wrongPredictionPoints = -5
    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    var leaderboardApiState: LeaderboardApiState by mutableStateOf(LeaderboardApiState.Loading)
        private set

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    score = userPreferencesRepository.getScore(),
                    scoreEvolution = userPreferencesRepository.getScoreEvolution(),
                )
            }
        }
        loadLeaderboard()
        viewModelScope.launch { updateLeaderboard() }
    }

    /**
     * Select or unselected a specific team.
     * @param teamId Id of the team.
     */
    fun toggleSelectedTeam(teamId: Int): Boolean? {
        var newSelectionState: Boolean? = null

        _uiState.update { currentState ->
            val updatedLeaderboard =
                currentState.leaderboard.map { team ->
                    if (team.id == teamId) {
                        // Handle max selections
                        if (!team.isSelected && getSelectedTeams().count() == maxSelectableTeams) {
                            return null
                        }

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

    /**
     * Allow user to edit his team selection.
     */
    fun editSelection() {
        _uiState.update { currentState ->
            currentState.copy(isEditing = true)
        }
    }

    /**
     * Save the user's team selection and disable any edition.
     */
    fun confirmSelection() {
        _uiState.update { currentState ->
            currentState.copy(isEditing = false)
        }
        viewModelScope.launch { saveLeaderboard() }
    }

    /**
     * Retrieve the list of selected teams.
     * @returns The list of selected teams.
     */
    private fun getSelectedTeams(): List<Team> {
        return _uiState.value.leaderboard.filter { it.isSelected }
    }

    /**
     * Fetch real-time leaderboard data from API asynchronously and store the API state.
     */
    private suspend fun fetchLeaderboard() {
        leaderboardApiState =
            try {
                val leaderboardResult = LeaderboardApi.retrofitService.getLeaderboard()
                LeaderboardApiState.Success(leaderboardResult.asDomainObjects())
            } catch (e: IOException) {
                println("Failed to fetch leaderboard: $e")
                LeaderboardApiState.Error
            }
    }

    /**
     * Load leaderboard from user device and update UI.
     */
    private fun loadLeaderboard() {
        viewModelScope.launch {
            teamsRepository.getTeams().let { savedLeaderboard ->
                if (savedLeaderboard.isNotEmpty()) {
                    _uiState.update { currentState ->
                        currentState.copy(leaderboard = savedLeaderboard)
                    }
                    leaderboardApiState = LeaderboardApiState.Success(_uiState.value.leaderboard)
                }
            }
        }
    }

    /**
     * Save leaderboard content to the user device.
     */
    private suspend fun saveLeaderboard() {
        teamsRepository.replaceTeams(_uiState.value.leaderboard)
    }

    /**
     * Compare the actual and fetched leaderboard from API.
     * If they are different, update the score, leaderboard and save it to the user's device.
     */
    private suspend fun updateLeaderboard() {
        viewModelScope.async { fetchLeaderboard() }.await()

        if (leaderboardApiState is LeaderboardApiState.Success) {
            val newLeaderboard = (leaderboardApiState as LeaderboardApiState.Success).leaderboard
            if (!compareLeaderboard(newLeaderboard)) {
                updateScore(newLeaderboard)
                _uiState.update { currentState -> currentState.copy(leaderboard = newLeaderboard) }
                saveLeaderboard()
            }
        }
    }

    /**
     * Compare a given leaderboard with the current one.
     */
    private fun compareLeaderboard(newLeaderboard: List<Team>): Boolean {
        return newLeaderboard == _uiState.value.leaderboard
    }

    /**
     * Update the user score based on the user's team selection.
     * The previously stored leaderboard with team selection is compared with the new one.
     * Score and scoreEvolution are updated in the UI, and saved to the user device.
     */
    private fun updateScore(newLeaderboard: List<Team>) {
        var scoreEvolution = 0

        viewModelScope.launch {
            teamsRepository.getTeams().let { savedLeaderboard ->
                savedLeaderboard
                    .filter { it.isSelected }
                    .forEach { team ->
                        scoreEvolution +=
                            if (!newLeaderboard.map { it.id }.contains(team.id)) {
                                (newLeaderboard.count() - team.place + 1) * wrongPredictionPoints
                            } else {
                                newLeaderboard
                                    .find { it.id == team.id }!!.change
                                    .let { teamChange ->
                                        if (teamChange >= 0) {
                                            teamChange * correctPredictionPoints
                                        } else {
                                            abs(teamChange) * wrongPredictionPoints
                                        }
                                    }
                            }
                    }
                _uiState.update { currentState ->
                    currentState.copy(
                        score = currentState.score + scoreEvolution,
                        scoreEvolution = scoreEvolution,
                    )
                }
                userPreferencesRepository.saveScore(_uiState.value.score)
                userPreferencesRepository.saveScoreEvolution(_uiState.value.scoreEvolution)
            }
        }
    }
}
