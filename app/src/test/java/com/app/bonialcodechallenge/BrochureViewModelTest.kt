package com.app.bonialcodechallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.bonialcodechallenge.data.remote.dto.Brochure
import com.app.bonialcodechallenge.data.remote.dto.ContentItem
import com.app.bonialcodechallenge.domain.usecases.GetBrochuresUseCase
import com.app.bonialcodechallenge.presentation.viewmodel.BrochureViewModel
import com.app.bonialcodechallenge.presentation.viewmodel.UiState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class BrochureViewModelTest {
    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule() // New rule for coroutines

    private lateinit var viewModel: BrochureViewModel
    private lateinit var fakeRepository: FakeBrochureRepository

    @Before
    fun setup() {
        fakeRepository = FakeBrochureRepository()
        val useCase = GetBrochuresUseCase(fakeRepository)
        viewModel = BrochureViewModel(useCase)
    }

    @Test
    fun `filters by distance correctly`() = runTest {
        // Given
        val testContent = mapOf(
            "brochureImage" to "img1",
            "id" to "1",
            "publisher" to mapOf("name" to "Pub1"),
            "distance" to 4.0
        )
        fakeRepository.addBrochures(
            ContentItem("standard", testContent),
            ContentItem("standard", testContent + ("distance" to 6.0))
        )

        // When
        viewModel.toggleDistanceFilter() // Enable filter
        viewModel.loadContents()

        // Advance coroutines
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertTrue(state is UiState.Success)
        assertEquals(1, (state as UiState.Success).data.size)
        assertEquals(4.0, state.data.first().distance, 0.0)
    }

    @Test
    fun `shows loading and success states`() = runTest {
        // Given
        val testContent = mapOf(
            "brochureImage" to "img1",
            "id" to "1",
            "publisher" to mapOf("name" to "Pub1"),
            "distance" to 1.0
        )
        fakeRepository.addBrochures(ContentItem("standard", testContent))

        // Collect states
        val states = mutableListOf<UiState<List<Brochure>>>()
        val job = viewModel.uiState.onEach { states.add(it) }.launchIn(this)

        // When
        viewModel.loadContents()
        advanceUntilIdle() // Important!

        // Then
        assertEquals(2, states.size)
        assertTrue(states[0] is UiState.Loading)
        assertTrue(states[1] is UiState.Success)

        job.cancel()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainCoroutineRule : TestWatcher() {
    private val testDispatcher = StandardTestDispatcher()

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
