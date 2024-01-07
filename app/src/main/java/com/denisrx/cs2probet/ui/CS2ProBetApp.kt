package com.denisrx.cs2probet.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.denisrx.cs2probet.components.BottomNavigationBar
import com.denisrx.cs2probet.navigation.CS2ProBetScreen
import com.denisrx.cs2probet.navigation.NavComponent

@Composable
fun CS2ProBetApp(navController: NavHostController = rememberNavController()) {
    val goHome = { navController.navigate(CS2ProBetScreen.Home.name) {launchSingleTop = true} }
    val goToAbout = { navController.navigate(CS2ProBetScreen.About.name) {launchSingleTop = true} }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                actions = listOf(goHome, goToAbout),
            )
        },
    ) { innerPadding ->
        NavComponent(navController, modifier = Modifier.padding(innerPadding))
    }
}
