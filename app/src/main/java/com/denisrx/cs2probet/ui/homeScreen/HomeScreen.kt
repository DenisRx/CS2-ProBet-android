package com.denisrx.cs2probet.ui.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.denisrx.cs2probet.R
import com.denisrx.cs2probet.R.dimen.medium_padding
import com.denisrx.cs2probet.components.TeamListItem
import com.denisrx.cs2probet.data.LeaderboardSampler
import com.denisrx.cs2probet.ui.errorScreen.ErrorScreen
import com.denisrx.cs2probet.ui.loadingScreen.LoadingScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(),
) {
    val homeUiState by homeViewModel.uiState.collectAsState()

    when(homeViewModel.leaderboardApiState) {
        LeaderboardApiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
        LeaderboardApiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is LeaderboardApiState.Success -> HomeScreenContent(
            homeViewModel = homeViewModel,
            homeUiState = homeUiState,
            modifier = modifier.fillMaxSize(),
        )
    }
}

@Composable
fun HomeScreenContent(
    homeViewModel: HomeViewModel,
    homeUiState: HomeUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.app_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.large_padding))
                .wrapContentHeight()
        ) {
            Text(
                text ="Score: ${homeUiState.score}",
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Row(modifier = Modifier.weight(1f)) {
            Leaderboard(
                homeViewModel = homeViewModel,
                homeUiState = homeUiState,
            )
        }
        Row(
            modifier = Modifier
                .padding(top = dimensionResource(medium_padding))
                .wrapContentHeight()
        ) {
            EditionButtons(
                homeViewModel = homeViewModel,
                homeUiState = homeUiState,
            )
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
            items(homeUiState.leaderboard, key = { it.id }) {
                TeamListItem(
                    team = it,
                    homeViewModel = homeViewModel,
                    homeUiState = homeUiState,
                )
            }
        }
    }
}

@Composable
fun EditionButtons(
    homeViewModel: HomeViewModel,
    homeUiState: HomeUiState,
    modifier: Modifier = Modifier,
) {
    if (homeUiState.isEditing) {
        ElevatedButton(
            onClick = { homeViewModel.confirmSelection() },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = colorResource(R.color.custom_green),
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            modifier = modifier.size(
                width = dimensionResource(R.dimen.elevated_buttons_width),
                height = dimensionResource(R.dimen.elevated_buttons_height),
            ),
        ) {
            Text(stringResource(R.string.confirm_button_content))
        }
    } else {
        ElevatedButton(
            onClick = { homeViewModel.editSelection() },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = dimensionResource(R.dimen.elevated_buttons_default_elevation),
                pressedElevation = dimensionResource(R.dimen.elevated_buttons_pressed_elevation),
            ),
            modifier = modifier.size(
                width = dimensionResource(R.dimen.elevated_buttons_width),
                height = dimensionResource(R.dimen.elevated_buttons_height),
            ),
        ) {
            Text(stringResource(R.string.edit_button_content))
        }
    }
}

@Preview
@Composable
fun HomeScreenContentPreview() {
    HomeScreenContent(
        homeViewModel = viewModel(),
        homeUiState = HomeUiState(LeaderboardSampler.leaderboard),
    )
}
