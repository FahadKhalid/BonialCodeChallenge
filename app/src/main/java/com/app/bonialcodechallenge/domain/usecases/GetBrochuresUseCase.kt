package com.app.bonialcodechallenge.domain.usecases

import com.app.bonialcodechallenge.domain.repository.BrochureRepository
import com.app.bonialcodechallenge.domain.util.Brochure
import com.app.bonialcodechallenge.domain.util.BrochureType

class GetBrochuresUseCase(private val repository: BrochureRepository) {
    suspend operator fun invoke(): Result<List<Brochure>> {
        return repository.getFilteredContents().map { data ->
            data.map { item ->

                val content = item.content as? Map<*, *>
                val publisherMap = content?.get("publisher") as? Map<*, *>
                val brochureImage = content?.get("brochureImage") as? String ?: ""
                val id = content?.get("id") as? Double ?: 0.0
                val publisherName = publisherMap?.get("name") as? String ?: "Unknown Publisher"
                val distance = content?.get("distance") as? Double ?: 0.0
                val brochureType =  BrochureType.fromString(item.contentType)

                if (brochureType == BrochureType.Premium) {
                    Brochure.PremiumBrochure(
                        imageUrl = brochureImage,
                        id = id,
                        distance = distance,
                        name = publisherName,
                        brochureType = brochureType,
                    )
                } else {
                    Brochure.StandardBrochure(
                        imageUrl = brochureImage,
                        id = id,
                        distance = distance,
                        name = publisherName,
                        brochureType = brochureType
                    )
                }
            }
        }
    }
}
