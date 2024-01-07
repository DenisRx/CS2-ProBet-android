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
import com.denisrx.cs2probet.ui.AppViewModelProvider
import com.denisrx.cs2probet.ui.errorScreen.ErrorScreen
import com.denisrx.cs2probet.ui.loadingScreen.LoadingScreen

/**
 * Main screen displaying the correct content depending on the loading state.
 * @param modifier The modifier to be applied to the [HomeScreen].
 * @param homeViewModel The [HomeViewModel] used to update the UI.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val homeUiState by homeViewModel.uiState.collectAsState()

    when (homeViewModel.leaderboardApiState) {
        LeaderboardApiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
        LeaderboardApiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is LeaderboardApiState.Success ->
            HomeScreenContent(
                homeViewModel = homeViewModel,
                homeUiState = homeUiState,
                modifier = modifier.fillMaxSize(),
            )
    }
}

/**
 * Content displayed on the [HomeScreen] containing the score and leaderboard.
 * @param homeViewModel The [HomeViewModel] used to update the UI.
 * @param homeUiState The [HomeUiState] used to retrieve the logic data.
 * @param modifier The modifier to be applied to the [HomeScreenContent].
 */
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
            modifier =
                Modifier
                    .padding(bottom = dimensionResource(R.dimen.large_padding))
                    .wrapContentHeight(),
        ) {
            Scores(homeUiState)
        }
        Row(modifier = Modifier.weight(1f)) {
            Leaderboard(
                homeViewModel = homeViewModel,
                homeUiState = homeUiState,
            )
        }
        Row(
            modifier =
                Modifier
                    .padding(top = dimensionResource(medium_padding))
                    .wrapContentHeight(),
        ) {
            EditionButtons(
                homeViewModel = homeViewModel,
                homeUiState = homeUiState,
            )
        }
    }
}

/**
 * Component responsible for displaying the leaderboard.
 * The leaderboard is a scrollable list of teams, which can be selected/unselected.
 * @param homeViewModel The [HomeViewModel] used to update the UI.
 * @param homeUiState The [HomeUiState] used to retrieve the logic data.
 * @param modifier The modifier to be applied to the [Leaderboard].
 */
@Composable
fun Leaderboard(
    homeViewModel: HomeViewModel,
    homeUiState: HomeUiState,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()

    Card {
        LazyColumn(
            state = lazyListState,
            modifier = modifier.padding(dimensionResource(R.dimen.list_content_padding)),
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

/**
 * The editions buttons are displayed regarding the edition status.
 * When the user is editing his team selection, the confirm button appears.
 * Otherwise, an "edit" button is displayed the rest of the time.
 * @param homeViewModel The [HomeViewModel] used to update the UI.
 * @param homeUiState The [HomeUiState] used to retrieve the logic data.
 * @param modifier The modifier to be applied to the [EditionButtons].
 */
@Composable
fun EditionButtons(
    homeViewModel: HomeViewModel,
    homeUiState: HomeUiState,
    modifier: Modifier = Modifier,
) {
    if (homeUiState.isEditing) {
        ElevatedButton(
            onClick = { homeViewModel.confirmSelection() },
            colors =
                ButtonDefaults.elevatedButtonColors(
                    containerColor = colorResource(R.color.custom_green),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            modifier =
                modifier.size(
                    width = dimensionResource(R.dimen.elevated_buttons_width),
                    height = dimensionResource(R.dimen.elevated_buttons_height),
                ),
        ) {
            Text(stringResource(R.string.confirm_button_content))
        }
    } else {
        ElevatedButton(
            onClick = { homeViewModel.editSelection() },
            colors =
                ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            elevation =
                ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = dimensionResource(R.dimen.elevated_buttons_default_elevation),
                    pressedElevation = dimensionResource(R.dimen.elevated_buttons_pressed_elevation),
                ),
            modifier =
                modifier.size(
                    width = dimensionResource(R.dimen.elevated_buttons_width),
                    height = dimensionResource(R.dimen.elevated_buttons_height),
                ),
        ) {
            Text(stringResource(R.string.edit_button_content))
        }
    }
}

/**
 * Component used to display the score information.
 * The user score is shown as a title and a description with the score evolution appears below.
 * @param homeUiState The [HomeUiState] used to retrieve the logic data.
 */
@Composable
fun Scores(homeUiState: HomeUiState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Text(
                text = stringResource(R.string.score, homeUiState.score),
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Row {
            Text(
                text = "${formatScoreEvolution(homeUiState.scoreEvolution)} points this week",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

/**
 * Formats the score evolution depending on its positiveness.
 * When the score evolution is positive or equal to zero, it's rendered preceded by a "+" sign.
 * @param value Score evolution to format
 * @returns The formatted score evolution as a string
 */
private fun formatScoreEvolution(value: Int): String {
    return if (value >= 0) {
        "+$value"
    } else {
        value.toString()
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
