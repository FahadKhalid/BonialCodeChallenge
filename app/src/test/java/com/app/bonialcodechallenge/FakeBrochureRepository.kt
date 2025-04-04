package com.app.bonialcodechallenge

import com.app.bonialcodechallenge.data.remote.dto.ContentItem
import com.app.bonialcodechallenge.domain.repository.BrochureRepository

class FakeBrochureRepository : BrochureRepository {
    private val fakeData = mutableListOf<ContentItem>()
    var shouldReturnError = false
    var errorToReturn: Exception = RuntimeException("Test error")

    fun addBrochures(vararg items: ContentItem) {
        fakeData.addAll(items)
    }

    override suspend fun getFilteredContents(): Result<List<ContentItem>> {
        if (shouldReturnError) {
            return Result.failure(errorToReturn)
        }
        return Result.success(fakeData.filter {
            it.contentType != "superBannerCarousel" && it.content != null
        })
    }
}