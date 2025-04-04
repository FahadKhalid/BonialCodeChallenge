package com.app.bonialcodechallenge.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.bonialcodechallenge.R

@Composable
fun FilterSwitches(
    filterByDistance: Boolean,
    filterByPremium: Boolean,
    onDistanceFilterChange: (Boolean) -> Unit,
    onPremiumFilterChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        FilterSwitch(
            label = stringResource(R.string.filter_distance),
            checked = filterByDistance,
            onCheckedChange = onDistanceFilterChange
        )

        FilterSwitch(
            label = stringResource(R.string.filter_premium),
            checked = filterByPremium,
            onCheckedChange = onPremiumFilterChange
        )
    }
}

@Composable
private fun FilterSwitch(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label)
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}