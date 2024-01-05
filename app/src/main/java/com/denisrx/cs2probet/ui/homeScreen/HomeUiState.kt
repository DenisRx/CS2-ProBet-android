package com.denisrx.cs2probet.ui.homeScreen

import com.denisrx.cs2probet.model.Team

data class HomeUiState(
    val leaderboard: List<Team> = emptyList(),
    val isEditing: Boolean = false,
)
