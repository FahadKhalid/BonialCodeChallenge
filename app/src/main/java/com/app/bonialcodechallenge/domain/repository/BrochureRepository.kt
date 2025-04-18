package com.app.bonialcodechallenge.domain.repository

import com.app.bonialcodechallenge.data.remote.dto.ContentItem

interface BrochureRepository {
    suspend fun getFilteredContents(): Result<List<ContentItem>>
}