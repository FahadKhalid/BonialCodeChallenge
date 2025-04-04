package com.app.bonialcodechallenge.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.app.bonialcodechallenge.domain.util.Brochure
import com.app.bonialcodechallenge.domain.util.BrochureType

@Composable
fun BrochureList(data: List<Brochure>) {
    val configuration = LocalConfiguration.current
    val columns = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        data.forEach { brochure ->
            if (brochure.brochureType == BrochureType.Premium) {
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