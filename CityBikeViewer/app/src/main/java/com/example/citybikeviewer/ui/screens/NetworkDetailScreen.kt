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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkDetailScreen(
    networkId: String,
    onBackClick: () -> Unit,
    viewModel: CityBikeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val network = (uiState as? CityBikeUiState.Success)?.networks?.find { it.id == networkId }

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
                        Text(text = "Network ID:", style = MaterialTheme.typography.labelLarge)
                        Text(text = network.id, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = "Status:", style = MaterialTheme.typography.labelLarge)
                        Text(text = "Active", style = MaterialTheme.typography.bodyLarge)

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = "Coordinates:", style = MaterialTheme.typography.labelLarge)
                        Text(
                            text = "Lat: ${network.location.latitude}\nLon: ${network.location.longitude}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            } else {
                CircularProgressIndicator()
            }
        }
    }
}