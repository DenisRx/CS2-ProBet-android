package com.denisrx.cs2probet.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.ui.graphics.vector.ImageVector
import com.denisrx.cs2probet.R

enum class CS2ProBetScreen(@StringRes val label: Int, val icon: ImageVector) {
    Home(label = R.string.home_screen_nav_title, icon = Icons.Filled.Leaderboard),
    About(label = R.string.about_screen_nav_title, icon = Icons.Filled.Info),
}
