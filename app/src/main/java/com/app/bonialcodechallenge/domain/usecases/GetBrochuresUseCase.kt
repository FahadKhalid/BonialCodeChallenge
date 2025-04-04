package com.app.bonialcodechallenge.domain.usecases

import com.app.bonialcodechallenge.data.remote.dto.Brochure
import com.app.bonialcodechallenge.domain.repository.ContentRepository

class GetBrochuresUseCase(private val repository: ContentRepository) {
    suspend operator fun invoke(): Result<List<Brochure>> {
        return repository.getFilteredContents().map { data ->
            data.map { item ->
                val content = item.content as? Map<*, *>

                // Extract publisher map
                val publisherMap = content?.get("publisher") as? Map<*, *>
                val brochureImage = content?.get("brochureImage") as? String ?: ""
                val id = content?.get("id") as? Double ?: 0.0
                val publisherName = publisherMap?.get("name") as? String ?: "Unknown Publisher"
                val distance = content?.get("distance") as? Double ?: 0.0
                val contentType = item.contentType

                Brochure(
                    imageUrl = brochureImage,
                    name = publisherName,
                    distance = distance,
                    id = id,
                    contentType = contentType
                )
            }
        }
    }
}
