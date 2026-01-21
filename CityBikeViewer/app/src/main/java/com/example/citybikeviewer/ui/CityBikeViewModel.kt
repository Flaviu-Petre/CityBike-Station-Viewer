package com.example.citybikeviewer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citybikeviewer.data.CityBikeRepository
import com.example.citybikeviewer.data.local.FavoriteNetwork
import com.example.citybikeviewer.data.model.Network
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.citybikeviewer.data.WeatherRepository
import com.example.citybikeviewer.data.model.WeatherResponse

@HiltViewModel
class CityBikeViewModel @Inject constructor(
    private val repository: CityBikeRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<CityBikeUiState>(CityBikeUiState.Loading)
    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val weatherState: StateFlow<WeatherUiState> = _weatherState.asStateFlow()

    val uiState: StateFlow<CityBikeUiState> = _uiState.asStateFlow()

    init {
        getNetworks()
    }

    fun getNetworks() {
        viewModelScope.launch {
            _uiState.value = CityBikeUiState.Loading
            try {
                val networks = repository.getNetworks()
                _uiState.value = CityBikeUiState.Success(networks)
            } catch (e: Exception) {
                _uiState.value = CityBikeUiState.Error("Failed to load: ${e.message}")
            }
        }
    }

    fun saveNetwork(network: Network) {
        viewModelScope.launch {
            repository.addToFavorites(network)
            println("DEBUG: Saved ${network.name} to DB")
        }
    }

    fun deleteNetwork(network: FavoriteNetwork) {
        viewModelScope.launch {
            repository.removeFromFavorites(
                networkId = network.id,
                name = network.name,
                city = network.city,
                country = network.country
            )
        }
    }

    fun toggleFavorite(network: Network) {
        viewModelScope.launch {
            val isFavorite = favoriteIds.value.contains(network.id)
            if (isFavorite) {
                repository.removeFromFavorites(network.id, network.name, network.location.city, network.location.country)
            } else {
                repository.addToFavorites(network)
            }
        }
    }

    val favoriteNetworks = repository.getFavorites()

    val favoriteIds = repository.getFavorites()
        .map { list -> list.map { it.id }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())


    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            _weatherState.value = WeatherUiState.Loading
            try {
                val response = weatherRepository.getWeather(lat, lon)
                _weatherState.value = WeatherUiState.Success(response)
            } catch (e: Exception) {
                _weatherState.value = WeatherUiState.Error("Weather N/A")
            }
        }
    }

}

