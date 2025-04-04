package com.app.bonialcodechallenge.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.bonialcodechallenge.R
import com.app.bonialcodechallenge.data.remote.dto.Brochure
import com.app.bonialcodechallenge.presentation.viewmodel.ContentViewModel
import com.app.bonialcodechallenge.presentation.viewmodel.UiState
import org.koin.androidx.compose.koinViewModel
@Composable
fun ContentScreen(viewModel: ContentViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val filterByDistance by viewModel.filterByDistance.collectAsState()
    val filterByBrochureType by viewModel.filterByPremium.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadContents()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // 🔘 Distance Filter Switch
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Show only within 5 km")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = filterByDistance,
                onCheckedChange = { viewModel.toggleDistanceFilter() }
            )
        }

        // 🔘 Premium Filter Switch
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Show Premium Brochures")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = filterByBrochureType,
                onCheckedChange = { viewModel.togglePremiumFilter() }
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when (val state = uiState) {
                is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is UiState.Error -> state.message?.let {
                    Text(
                        text = it,
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Red
                    )
                }

                is UiState.Success -> {
                    val items = state.data
                    ContentList(items) // Pass list of filtered BrochureResponse
                }
            }
        }
    }
}

@Composable
private fun ContentList(data: List<Brochure>) {
    val configuration = LocalConfiguration.current
    val columns = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        data.forEach { brochure ->
            if (brochure.contentType == "brochurePremium") {
                item(key = brochure.id, span = { GridItemSpan(columns) }) {
                    ContentItemView(brochure, isFullWidth = true)
                }
            } else {
                item(key = brochure.id) {
                    ContentItemView(brochure, isFullWidth = false)
                }
            }
        }
    }
}

@Composable
fun ContentItemView(item: Brochure, isFullWidth: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = item.name,
                fontSize = if (isFullWidth) 20.sp else 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .crossfade(true)
                    .build(),
                contentDescription = "Brochure Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isFullWidth) 250.dp else 200.dp) // Bigger image for full-width
                    .clip(RoundedCornerShape(if (isFullWidth) 12.dp else 8.dp)), // Slightly more rounded corners
                contentScale = ContentScale.Crop
            )
        }
    }
}