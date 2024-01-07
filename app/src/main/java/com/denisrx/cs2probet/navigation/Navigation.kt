package com.denisrx.cs2probet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.denisrx.cs2probet.ui.aboutScreen.AboutScreen
import com.denisrx.cs2probet.ui.homeScreen.HomeScreen

/**
 * Display the correct screen based on the [navController].
 */
@Composable
fun NavComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = CS2ProBetScreen.Home.name,
        modifier = modifier,
    ) {
        composable(route = CS2ProBetScreen.Home.name) {
            HomeScreen()
        }
        composable(route = CS2ProBetScreen.About.name) {
            AboutScreen()
        }
    }
}
