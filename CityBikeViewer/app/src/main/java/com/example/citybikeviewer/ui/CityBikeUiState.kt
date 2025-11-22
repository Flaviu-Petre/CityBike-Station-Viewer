package com.example.citybikeviewer.ui

import com.example.citybikeviewer.data.model.Network

sealed interface CityBikeUiState {
    data object Loading : CityBikeUiState
    data class Success(val networks: List<Network>) : CityBikeUiState
    data class Error(val message: String) : CityBikeUiState
}