package com.denisrx.cs2probet.ui.loadingScreen

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Component used to show a loading animation while the data is being retrieved.
 * @param modifier The modifier to be applied to the [LoadingScreen]
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = modifier.wrapContentSize(Alignment.Center))
}
