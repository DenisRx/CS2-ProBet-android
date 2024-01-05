package com.denisrx.cs2probet.ui.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.denisrx.cs2probet.R
import com.denisrx.cs2probet.components.TeamListItem

@Composable
fun HomeScreen() {
    Scaffold { innerPadding ->
        HomeScreenContent(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(),
) {
    val homeUiState by homeViewModel.uiState.collectAsState()

    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.app_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Leaderboard(
            homeViewModel = homeViewModel,
            homeUiState = homeUiState,
            modifier = modifier
        )

        Row(modifier = modifier.padding(dimensionResource(R.dimen.medium_padding))) {
            if (homeUiState.isEditing) {
                Button(
                    onClick = { homeViewModel.confirmSelection() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.custom_green),
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                    ),
                ) {
                    Text("Confirm")
                }
            } else {
                Button(onClick = { homeViewModel.editSelection() }) {
                    Text("Edit")
                }
            }
        }
    }
}

@Composable
fun Leaderboard(
    homeViewModel: HomeViewModel,
    homeUiState: HomeUiState,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    Card {
        LazyColumn(
            state = lazyListState,
            modifier = modifier.padding(dimensionResource(R.dimen.list_content_padding))
        ) {
            items(homeUiState.leaderboard) {
                TeamListItem(
                    team = it,
                    homeViewModel = homeViewModel,
                    homeUiState = homeUiState,
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenContent()
}
