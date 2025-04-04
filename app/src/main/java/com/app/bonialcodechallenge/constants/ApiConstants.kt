package com.app.bonialcodechallenge.constants

import com.app.bonialcodechallenge.BuildConfig

internal object ApiConstants {
    fun getBaseUrl(): String = BuildConfig.BASE_URL
}