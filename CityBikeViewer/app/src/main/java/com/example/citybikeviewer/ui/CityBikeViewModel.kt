package com.example.citybikeviewer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citybikeviewer.data.CityBikeRepository
import com.example.citybikeviewer.data.model.Network
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityBikeViewModel @Inject constructor(
    private val repository: CityBikeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<CityBikeUiState>(CityBikeUiState.Loading)

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

    val favoriteNetworks = repository.getFavorites()
}