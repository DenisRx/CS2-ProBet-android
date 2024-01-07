package com.denisrx.cs2probet.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.denisrx.cs2probet.navigation.NavComponent

@Composable
fun CS2ProBetApp(navController: NavHostController = rememberNavController()) {
    Scaffold { innerPadding ->
        NavComponent(navController, modifier = Modifier.padding(innerPadding))
    }
}
