package com.denisrx.cs2probet.ui.homeScreen

import com.denisrx.cs2probet.model.Team

sealed interface LeaderboardApiState{
    data class Success(val leaderboard: List<Team>) : LeaderboardApiState
    data object Error: LeaderboardApiState
    data object Loading : LeaderboardApiState
}

data class HomeUiState(
    val leaderboard: List<Team> = emptyList(),
    val isEditing: Boolean = false,
    val score: Int = 0,
)
