package com.example.citybikeviewer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.citybikeviewer.ui.CityBikeUiState
import com.example.citybikeviewer.ui.CityBikeViewModel
import androidx.compose.runtime.LaunchedEffect
import com.example.citybikeviewer.ui.WeatherUiState
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkDetailScreen(
    networkId: String,
    onBackClick: () -> Unit,
    viewModel: CityBikeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val weatherState by viewModel.weatherState.collectAsState()

    val network = (uiState as? CityBikeUiState.Success)?.networks?.find { it.id == networkId }

    LaunchedEffect(network) {
        if (network != null) {
            viewModel.fetchWeather(network.location.latitude, network.location.longitude)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Station Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (network != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(text = "Location", style = MaterialTheme.typography.titleMedium)
                        Text(text = "${network.location.city}, ${network.location.country}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Lat: ${network.location.latitude} | Lon: ${network.location.longitude}")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                //Display Weather
                Text(text = "Current Weather", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))

                when (val weather = weatherState) {
                    is WeatherUiState.Loading -> CircularProgressIndicator()
                    is WeatherUiState.Error -> Text(text = weather.message, color = MaterialTheme.colorScheme.error)
                    is WeatherUiState.Success -> {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                AsyncImage(
                                    model = "https:${weather.weather.current.condition.icon}",
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text(
                                        text = "${weather.weather.current.tempC}°C",
                                        style = MaterialTheme.typography.displayMedium
                                    )
                                    Text(
                                        text = weather.weather.current.condition.text,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(text = "Humidity: ${weather.weather.current.humidity}%")
                                }
                            }
                        }
                    }
                    else -> {}
                }

            } else {
                CircularProgressIndicator()
            }
        }
    }
}