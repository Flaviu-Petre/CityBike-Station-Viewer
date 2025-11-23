package com.example.citybikeviewer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.citybikeviewer.data.local.FavoriteNetwork
import com.example.citybikeviewer.ui.CityBikeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: CityBikeViewModel = hiltViewModel()
) {
    val favorites by viewModel.favoriteNetworks.collectAsState(initial = emptyList())

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("My Saved Stations") }) }
    ) { paddingValues ->
        if (favorites.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("No favorites yet. Go back and add some!")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(favorites) { network ->
                    FavoriteItem(network)
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(network: FavoriteNetwork) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = network.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "${network.city}, ${network.country}", style = MaterialTheme.typography.bodySmall)
            }
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Saved", tint = MaterialTheme.colorScheme.onTertiaryContainer)
        }
    }
}