package com.app.bonialcodechallenge.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
//
//@Composable
//fun BrochureGrid(
//    brochures: List<BrochureItem>,
//    isLandscape: Boolean
//) {
//    val columns = if (isLandscape) 3 else 2
//
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(columns),
//        modifier = Modifier.padding(8.dp)
//    ) {
//        items(
//            count = brochures.size,  // Correct way to specify item count
//            key = { index -> brochures[index].id }  // Use unique ID for stability
//        ) { index ->
//            val brochure = brochures[index]
//            BrochureCard(
//                brochure = brochure,
//                modifier = Modifier
//                    .padding(4.dp)
//                    .fillMaxWidth(
//                        if (brochure.contentType == "brochurePremium") 1f
//                        else 0.5f
//                    )
//            )
//        }
//    }
//}