package com.app.bonialcodechallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bonialcodechallenge.domain.usecases.GetBrochuresUseCase
import com.app.bonialcodechallenge.data.remote.dto.Brochure
import com.app.bonialcodechallenge.data.remote.dto.BrochureType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BrochureViewModel(private val getBrochuresUseCase: GetBrochuresUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Brochure>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Brochure>>> = _uiState

    private val _filterByDistance = MutableStateFlow(false)
    val filterByDistance: StateFlow<Boolean> = _filterByDistance

    private val _filterByPremium = MutableStateFlow(false)
    val filterByPremium: StateFlow<Boolean> = _filterByPremium

    // Reload data when switching filter
    fun toggleDistanceFilter() {
        _filterByDistance.value = !_filterByDistance.value
        loadContents()
    }

    // Reload data when switching filter
    fun togglePremiumFilter() {
        _filterByPremium.value = !_filterByPremium.value
        loadContents()
    }

    fun loadContents() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = getBrochuresUseCase()
            _uiState.value = result.fold(
                onSuccess = { data ->
                    val filteredData = data.filter { brochure ->
                        (!_filterByDistance.value || brochure.distance <= 5.0) &&
                                (!_filterByPremium.value || brochure.brochureType == BrochureType.Premium)
                    }
                    UiState.Success(filteredData)
                },
                onFailure = { UiState.Error(it.message) }
            )
        }
    }
}
