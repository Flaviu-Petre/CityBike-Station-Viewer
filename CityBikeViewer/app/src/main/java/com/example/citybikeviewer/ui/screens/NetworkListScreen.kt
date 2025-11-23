package com.example.citybikeviewer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.citybikeviewer.data.model.Network
import com.example.citybikeviewer.ui.CityBikeUiState
import com.example.citybikeviewer.ui.CityBikeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkListScreen(
    viewModel: CityBikeViewModel = hiltViewModel(),
    onNetworkClick: (String) -> Unit = {},
    onNavigateToFavorites: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("CityBike Networks") },
                actions = {
                    TextButton(onClick = onNavigateToFavorites) {
                        Text("Favorites")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when (val state = uiState) {
                is CityBikeUiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is CityBikeUiState.Error -> Text(text = state.message, modifier = Modifier.align(Alignment.Center))
                is CityBikeUiState.Success -> {
                    NetworkList(
                        networks = state.networks,
                        favoriteIds = favoriteIds,
                        onItemClick = onNetworkClick,
                        onToggleFavorite = { network -> viewModel.toggleFavorite(network) }
                    )
                }
            }
        }
    }
}

@Composable
fun NetworkList(
    networks: List<Network>,
    favoriteIds: Set<String>, // New Parameter
    onItemClick: (String) -> Unit,
    onToggleFavorite: (Network) -> Unit
) {
    LazyColumn {
        items(networks) { network ->
            val isFav = favoriteIds.contains(network.id)

            NetworkItem(
                network = network,
                isFavorite = isFav,
                onClick = onItemClick,
                onSaveClick = onToggleFavorite
            )
        }
    }
}

@Composable
fun NetworkItem(
    network: Network,
    isFavorite: Boolean,
    onClick: (String) -> Unit,
    onSaveClick: (Network) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(network.id) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
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

            IconButton(onClick = { onSaveClick(network) }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Toggle Favorite",
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}