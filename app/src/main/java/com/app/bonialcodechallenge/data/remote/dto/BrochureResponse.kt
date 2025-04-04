package com.app.bonialcodechallenge.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "_embedded")
    val embedded: EmbeddedContent?
)

@JsonClass(generateAdapter = true)
data class EmbeddedContent(
    @Json(name = "contents")
    val contents: List<ContentItem>?
)

@JsonClass(generateAdapter = true)
data class ContentItem(
    @Json(name = "contentType")
    val contentType: String?,

    @Json(name = "content")
    val content: Any?
)

@JsonClass(generateAdapter = true)
data class BrochureResponse(
    @Json(name = "brochureImage") val imageUrl: String,
    @Json(name = "id") val id: String,
    @Json(name = "publisher") val publisher: Publisher,
    @Json(name = "distance") val distance: Double,
    @Json(name = "contentType") val contentType: String,
)

@JsonClass(generateAdapter = true)
data class Publisher(
    @Json(name = "name") val name: String,
)