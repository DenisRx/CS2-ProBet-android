package com.denisrx.cs2probet.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import com.denisrx.cs2probet.R
import com.denisrx.cs2probet.model.Team
import com.denisrx.cs2probet.ui.homeScreen.HomeViewModel

@Composable
fun TeamListItem(
    team: Team,
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {
    var checked by remember { mutableStateOf(team.isSelected) }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(vertical = dimensionResource(R.dimen.list_row_horizontal_padding))
            .fillMaxWidth()
    ) {
        Column {
            Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))) {
                Text(text = "#${team.place}")
                Text(team.change.toString())
                Text(team.name)
            }
        }
        Icon(
            imageVector = if (checked) Icons.Filled.CheckCircle else Icons.Outlined.Circle,
            contentDescription = stringResource(R.string.select_team_button),
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.clickable(
                onClick = {
                    checked = !checked
                    homeViewModel.toggleSelectedTeam(team.id)
                },
                role = Role.Checkbox,
            )
        )
    }
}