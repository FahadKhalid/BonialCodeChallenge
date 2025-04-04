package com.app.bonialcodechallenge.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
        FilterSwitches(
            filterByDistance = filterByDistance,
            filterByPremium = filterByBrochureType,
            onDistanceFilterChange = { viewModel.toggleDistanceFilter() },
            onPremiumFilterChange = { viewModel.togglePremiumFilter() }
        )

        when (val state = uiState) {
            is UiState.Loading -> LoadingState()
            is UiState.Error -> ErrorState(
                message = state.message,
                onRetry = { viewModel.loadContents() }
            )

            is UiState.Success -> BrochureList(data = state.data)
        }
    }
}