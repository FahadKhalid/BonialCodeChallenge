package com.app.bonialcodechallenge.constants

internal object ApiConstants {
    // Private to restrict access within the module
    private const val BASE_URL = "https://mobile-s3-test-assets.aws-sdlc-bonial.com/"

    // Public getter to prevent direct field access
    fun getBaseUrl(): String = BASE_URL
}