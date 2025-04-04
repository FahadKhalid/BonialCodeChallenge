package com.app.bonialcodechallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bonialcodechallenge.data.remote.dto.Brochure
import com.app.bonialcodechallenge.domain.usecases.GetBrochuresUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContentViewModel(
    private val getBrochuresUseCase: GetBrochuresUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Brochure>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Brochure>>> = _uiState

    fun loadContents() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getBrochuresUseCase()
                .onSuccess { brochures ->
                    _uiState.value = UiState.Success(brochures)
                }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(exception.message ?: "Unknown error")
                }
        }
    }
}
