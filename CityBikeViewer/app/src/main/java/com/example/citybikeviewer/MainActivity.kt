package com.example.citybikeviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.citybikeviewer.ui.screens.FavoritesScreen
import com.example.citybikeviewer.ui.screens.NetworkDetailScreen
import com.example.citybikeviewer.ui.screens.NetworkListScreen
import com.example.citybikeviewer.ui.theme.CityBikeViewerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CityBikeViewerTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "list") {

                    composable("list") {
                        NetworkListScreen(
                            onNetworkClick = { networkId ->
                                navController.navigate("details/$networkId")
                            },
                            onNavigateToFavorites = { navController.navigate("favorites") }
                        )
                    }

                    composable("favorites") {
                        FavoritesScreen()
                    }

                    composable(
                        route = "details/{networkId}",
                        arguments = listOf(navArgument("networkId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val networkId = backStackEntry.arguments?.getString("networkId") ?: "Unknown"

                        NetworkDetailScreen(
                            networkId = networkId,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}