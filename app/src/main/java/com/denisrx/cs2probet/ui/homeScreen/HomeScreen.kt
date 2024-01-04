package com.denisrx.cs2probet.ui.homeScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.denisrx.cs2probet.R
import com.denisrx.cs2probet.components.TeamListItem

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val homeUiState by viewModel.uiState.collectAsState()

    Leaderboard(homeViewModel = viewModel, homeUiState = homeUiState)
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
                TeamListItem(team = it, homeViewModel = homeViewModel)
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
