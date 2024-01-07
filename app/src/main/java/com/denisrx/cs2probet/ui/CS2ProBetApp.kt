package com.denisrx.cs2probet.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.denisrx.cs2probet.navigation.BottomNavigationBar
import com.denisrx.cs2probet.navigation.CS2ProBetScreen
import com.denisrx.cs2probet.navigation.NavComponent

/**
 * This method is responsible for setting up the navigation controller and display the navigation bar.
 */
@Composable
fun CS2ProBetApp(navController: NavHostController = rememberNavController()) {
    var navigationSelectedItem by remember { mutableIntStateOf(0) }

    val navActions: List<() -> Unit> =
        CS2ProBetScreen.entries.mapIndexed { index, navigationItem ->
            {
                navController.navigate(navigationItem.name) { launchSingleTop = true }
                navigationSelectedItem = index
            }
        }

    DisposableEffect(navController) {
        val callback =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                navigationSelectedItem =
                    CS2ProBetScreen.entries.toTypedArray().indexOfFirst {
                        it.name == destination.route
                    }
            }
        navController.addOnDestinationChangedListener(callback)

        onDispose {
            navController.removeOnDestinationChangedListener(callback)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                actions = navActions,
                navigationSelectedItem = navigationSelectedItem,
            )
        },
    ) { innerPadding ->
        NavComponent(navController, modifier = Modifier.padding(innerPadding))
    }
}
