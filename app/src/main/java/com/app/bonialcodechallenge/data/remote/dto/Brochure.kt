package com.app.bonialcodechallenge.data.remote.dto

import androidx.compose.runtime.Immutable

@Immutable
sealed class Brochure {
    abstract val imageUrl: String
    abstract val id: Double
    abstract val distance: Double
    abstract val name: String
    abstract val brochureType: BrochureType?

    companion object {
        const val PUBLISHER = "publisher"
        const val ID = "id"
        const val NAME = "name"
        const val DISTANCE = "distance"
        const val BROCHURE_IMAGE = "brochureImage"
    }

    data class StandardBrochure(
        override val imageUrl: String,
        override val id: Double,
        override val distance: Double,
        override val name: String,
        override val brochureType: BrochureType?
    ) : Brochure()

    data class PremiumBrochure(
        override val imageUrl: String,
        override val id: Double,
        override val distance: Double,
        override val name: String,
        override val brochureType: BrochureType?
    ) : Brochure()
}


sealed class BrochureType {
    data object Standard : BrochureType()
    data object Premium : BrochureType()

    companion object {
        fun fromString(type: String?): BrochureType {
            return when (type?.lowercase()?.trim()) {
                "brochurepremium" -> Premium
                else -> Standard
            }
        }
    }
}