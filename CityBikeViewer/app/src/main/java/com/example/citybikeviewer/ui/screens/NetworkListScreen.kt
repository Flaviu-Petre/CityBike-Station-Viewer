package com.example.citybikeviewer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun NetworkListScreen(
    viewModel: CityBikeViewModel = hiltViewModel(),
    onNetworkClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("CityBike Networks") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is CityBikeUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is CityBikeUiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is CityBikeUiState.Success -> {
                    NetworkList(
                        networks = state.networks,
                        onItemClick = onNetworkClick
                    )
                }
            }
        }
    }
}

@Composable
fun NetworkList(
    networks: List<com.example.citybikeviewer.data.model.Network>,
    onItemClick: (String) -> Unit
) {
    LazyColumn {
        items(networks) { network ->
            NetworkItem(network = network, onClick = onItemClick)
        }
    }
}

@Composable
fun NetworkItem(
    network: com.example.citybikeviewer.data.model.Network,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(network.id) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = network.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${network.location.city}, ${network.location.country}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}