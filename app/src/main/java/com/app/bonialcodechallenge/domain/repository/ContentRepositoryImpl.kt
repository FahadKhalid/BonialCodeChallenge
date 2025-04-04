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
    override suspend fun getFilteredContents(): Result<List<ContentItem>> {
        return withContext(ioDispatcher) {
            try {
                val response = apiService.getShelf()
                val filtered = response.embedded?.contents?.filter { item ->
                    item.contentType != "superBannerCarousel" &&
                            item.content != null
                } ?: emptyList()
                Result.success(filtered)


            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}