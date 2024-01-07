package com.denisrx.cs2probet.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.denisrx.cs2probet.navigation.CS2ProBetScreen

@Composable
fun BottomNavigationBar(actions: List<() -> Unit>) {
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }

    NavigationBar {
        CS2ProBetScreen.entries.forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = index == navigationSelectedItem,
                label = { Text(stringResource(id = navigationItem.label)) },
                icon = {
                    Icon(
                        imageVector = navigationItem.icon,
                        contentDescription = stringResource(id = navigationItem.label)
                    )
                },
                onClick = {
                    navigationSelectedItem = index
                    actions[index]()
                }
            )
        }
    }
}