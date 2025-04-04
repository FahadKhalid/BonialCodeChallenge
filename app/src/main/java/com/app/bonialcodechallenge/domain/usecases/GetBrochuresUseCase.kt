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
                val publisherName = publisherMap?.get("name") as? String ?: "Unknown Publisher"

                Brochure(
                    imageUrl = content?.get("brochureImage") as? String ?: "",
                    name = publisherName
                )
            }
        }
    }
}
