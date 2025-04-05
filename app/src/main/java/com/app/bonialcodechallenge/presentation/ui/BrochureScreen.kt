package com.app.bonialcodechallenge.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.bonialcodechallenge.presentation.viewmodel.BrochureViewModel
import com.app.bonialcodechallenge.presentation.viewmodel.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun BrochureScreen(viewModel: BrochureViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val filterByDistance by viewModel.filterByDistance.collectAsState()
    val filterByBrochureType by viewModel.filterByPremium.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadContents()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        FilterControls(
            filterByDistance = filterByDistance,
            filterByPremium = filterByBrochureType,
            onDistanceFilterChange = { viewModel.toggleDistanceFilter() },
            onPremiumFilterChange = { viewModel.togglePremiumFilter() },
            modifier = Modifier.padding(top = 16.dp)
        )

        when (val state = uiState) {
            is UiState.Loading -> LoadingState()
            is UiState.Error -> ErrorState(
                onRetry = { viewModel.loadContents() }
            )

            is UiState.Success -> BrochureList(data = state.data)
        }
    }
}