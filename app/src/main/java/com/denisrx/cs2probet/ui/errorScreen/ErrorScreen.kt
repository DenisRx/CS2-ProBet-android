package com.denisrx.cs2probet.ui.errorScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.denisrx.cs2probet.R

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Filled.CloudOff,
            contentDescription = stringResource(R.string.connection_error_icon_label),
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(dimensionResource(R.dimen.extra_large_icon_size)),
        )
        Text(
            text = stringResource(R.string.leaderboard_loading_failed),
            modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding)),
        )
    }
}
