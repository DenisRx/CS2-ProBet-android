package com.denisrx.cs2probet.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.denisrx.cs2probet.ui.homeScreen.HomeScreen

@Composable
fun CS2ProBetApp() {
    Scaffold { innerPadding ->
        HomeScreen(modifier = Modifier.padding(innerPadding))
    }
}