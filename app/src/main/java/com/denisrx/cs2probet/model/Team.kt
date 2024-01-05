package com.denisrx.cs2probet.model

data class Team(
    val points: Int,
    val place: Int,
    val name: String,
    val id: Int,
    val change: Int,
    val isNew: Boolean,
    val isSelected: Boolean = false,
)
