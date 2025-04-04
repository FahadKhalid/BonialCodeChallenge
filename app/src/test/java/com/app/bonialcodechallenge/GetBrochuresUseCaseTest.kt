package com.app.bonialcodechallenge

import com.app.bonialcodechallenge.data.remote.dto.Brochure
import com.app.bonialcodechallenge.data.remote.dto.ContentItem
import com.app.bonialcodechallenge.domain.usecases.GetBrochuresUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

class GetBrochuresUseCaseTest {
    private val fakeRepository = FakeBrochureRepository()
    private val useCase = GetBrochuresUseCase(fakeRepository)

    @Test
    fun `maps ContentItem to Brochure correctly`() = runTest {
        // Given
        val testContent = mapOf(
            "brochureImage" to "image_url",
            "id" to "123",
            "publisher" to mapOf("name" to "Test Publisher"),
            "distance" to 3.5
        )
        fakeRepository.addBrochures(
            ContentItem("brochurePremium", testContent),
            ContentItem("standard", testContent)
        )

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        val brochures = result.getOrNull()!!
        assertEquals(2, brochures.size)

        assertTrue(brochures[0] is Brochure.PremiumBrochure)
        assertEquals("image_url", brochures[0].imageUrl)
    }

    @Test
    fun `handles repository errors`() = runTest {
        // Given
        fakeRepository.shouldReturnError = true
        fakeRepository.errorToReturn = IOException("Network error")

        // When
        val result = useCase()

        // Then
        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }
}