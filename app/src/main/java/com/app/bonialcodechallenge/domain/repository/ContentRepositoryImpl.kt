package com.app.bonialcodechallenge.domain.repository

import com.app.bonialcodechallenge.data.remote.ApiService
import com.app.bonialcodechallenge.data.remote.dto.ContentItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentRepositoryImpl(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ContentRepository {
    private val cache = mutableListOf<ContentItem>()

    override suspend fun getFilteredContents(): Result<List<ContentItem>> {
        return withContext(ioDispatcher) {
            try {
                if (cache.isNotEmpty()) {
                    return@withContext Result.success(cache)
                }

                val response = apiService.getShelf()
                val filtered = response.embedded?.contents?.filter {
                    it.contentType != "superBannerCarousel" && it.content != null
                } ?: emptyList()

                cache.clear()
                cache.addAll(filtered)

                Result.success(filtered)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
