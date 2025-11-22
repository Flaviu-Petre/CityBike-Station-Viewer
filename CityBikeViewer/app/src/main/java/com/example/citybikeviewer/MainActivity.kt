package com.example.citybikeviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                                // TODO: Navigate to details
                                // navController.navigate("details/$networkId")
                                println("Clicked on: $networkId")
                            }
                        )
                    }

                    // Future Screen 2: Details Screen
                }
            }
        }
    }
}