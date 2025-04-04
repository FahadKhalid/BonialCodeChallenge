package com.app.bonialcodechallenge.data.remote

import com.app.bonialcodechallenge.data.remote.dto.ApiResponse
import retrofit2.http.GET

interface ApiService {
    @GET("shelf.json")
    suspend fun getShelf(): ApiResponse
}