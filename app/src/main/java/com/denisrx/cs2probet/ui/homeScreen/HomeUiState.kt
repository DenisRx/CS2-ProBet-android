package com.denisrx.cs2probet.ui.homeScreen

import com.denisrx.cs2probet.model.Team

/**
 * Interface listing the different states of an API call.
 */
sealed interface LeaderboardApiState {
    data class Success(val leaderboard: List<Team>) : LeaderboardApiState

    data object Error : LeaderboardApiState

    data object Loading : LeaderboardApiState
}

/**
 * Holds the immutable values of the state used by the [HomeScreen] and [HomeViewModel]
 * @param leaderboard List of teams ordered from the first (1) to the last (30)
 * @param isEditing Status of the leaderboard's edition
 * @param score Score of the user
 * @param scoreEvolution Score evolution since the last leaderboard update
 */
data class HomeUiState(
    val leaderboard: List<Team> = emptyList(),
    val isEditing: Boolean = false,
    val score: Int = 0,
    val scoreEvolution: Int = 0,
)
