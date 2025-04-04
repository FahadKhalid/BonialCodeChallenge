package com.app.bonialcodechallenge.domain.usecases

import com.app.bonialcodechallenge.domain.repository.BrochureRepository
import com.app.bonialcodechallenge.data.remote.dto.Brochure
import com.app.bonialcodechallenge.data.remote.dto.Brochure.Companion.PUBLISHER
import com.app.bonialcodechallenge.data.remote.dto.Brochure.Companion.ID
import com.app.bonialcodechallenge.data.remote.dto.Brochure.Companion.DISTANCE
import com.app.bonialcodechallenge.data.remote.dto.Brochure.Companion.BROCHURE_IMAGE
import com.app.bonialcodechallenge.data.remote.dto.Brochure.Companion.NAME

import com.app.bonialcodechallenge.data.remote.dto.BrochureType

class GetBrochuresUseCase(private val repository: BrochureRepository) {

    suspend operator fun invoke(): Result<List<Brochure>> {
        return repository.getFilteredContents().map { data ->
            data.map { item ->

                val content = item.content as? Map<*, *>
                val publisherMap = content?.get(PUBLISHER) as? Map<*, *>
                val brochureImage = content?.get(BROCHURE_IMAGE) as? String ?: ""
                val id = content?.get(ID) as? Double ?: 0.0
                val publisherName = publisherMap?.get(NAME) as? String ?: "Unknown Publisher"
                val distance = content?.get(DISTANCE) as? Double ?: 0.0
                val brochureType = BrochureType.fromString(item.contentType)

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
