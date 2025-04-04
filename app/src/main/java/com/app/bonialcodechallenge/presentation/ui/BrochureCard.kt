package com.app.bonialcodechallenge.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.bonialcodechallenge.R
//
//@Composable
//fun BrochureCard(
//    modifier: Int = Modifier
//) {
//    Card(
//        modifier = modifier,
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column {
//            // Image with placeholder fallback
//            AsyncImage(
//                model = brochure.imageUrl ?: R.drawable.ic_launcher_background,
//                contentDescription = brochure.retailer,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .aspectRatio(4/3f),
//                contentScale = ContentScale.Crop
//            )
//
//            // Retailer name and distance
//            Text(
//                text = brochure.retailer,
//                style = MaterialTheme.typography.titleMedium,
//                modifier = Modifier.padding(8.dp)
//            )
//
//            brochure.distance?.takeIf { it <= 5 }?.let { distance ->
//                Text(
//                    text = "%.1f km".format(distance),
//                    style = MaterialTheme.typography.labelSmall,
//                    modifier = Modifier.padding(horizontal = 8.dp)
//                )
//            }
//        }
//    }
//}