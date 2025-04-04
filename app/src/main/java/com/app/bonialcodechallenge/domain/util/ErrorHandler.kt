package com.app.bonialcodechallenge.domain.util

import com.squareup.moshi.JsonDataException
import java.io.IOException

interface ErrorHandler {
    fun handleError(exception: Exception): Exception
}

class ErrorHandlerImpl : ErrorHandler {
    override fun handleError(exception: Exception): Exception {
        return when (exception) {
            is IOException -> IOException("Check your internet connection", exception)
            is JsonDataException -> IOException("Invalid server response", exception)
            else -> exception
        }
    }
}