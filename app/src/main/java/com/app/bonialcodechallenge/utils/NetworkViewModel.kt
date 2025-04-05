package com.app.bonialcodechallenge.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NetworkViewModel(
    private val networkObserver: NetworkObserver
) : ViewModel() {
    private val _networkStatus = MutableStateFlow(true)
    val networkStatus: StateFlow<Boolean> = _networkStatus

    init {
        viewModelScope.launch {
            networkObserver.networkStatus.collect { isConnected ->
                _networkStatus.value = isConnected
            }
        }
    }
}