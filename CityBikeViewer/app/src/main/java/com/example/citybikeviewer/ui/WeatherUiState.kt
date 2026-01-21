package com.example.citybikeviewer.ui

import com.example.citybikeviewer.data.model.WeatherResponse

sealed interface WeatherUiState {
    object Loading : WeatherUiState
    object Idle : WeatherUiState
    data class Success(val weather: WeatherResponse) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}