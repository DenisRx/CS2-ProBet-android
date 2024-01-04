package com.denisrx.cs2probet.ui.homeScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun toggleSelectedTeam(teamId: Int) {
        _uiState.update {  currentState ->
            val updatedLeaderboard = currentState.leaderboard.map { team ->
                if (team.id == teamId) {
                    team.copy(isSelected = !team.isSelected)
                } else {
                    team
                }
            }
            currentState.copy(leaderboard = updatedLeaderboard)
        }
    }
}
