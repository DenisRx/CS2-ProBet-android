package com.denisrx.cs2probet.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun BottomNavigationBar(
    actions: List<() -> Unit>,
    navigationSelectedItem: Int,
) {
    NavigationBar {
        CS2ProBetScreen.entries.forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = index == navigationSelectedItem,
                label = { Text(stringResource(id = navigationItem.label)) },
                icon = {
                    Icon(
                        imageVector = navigationItem.icon,
                        contentDescription = stringResource(id = navigationItem.label),
                    )
                },
                onClick = {
                    actions[index]()
                },
            )
        }
    }
}
