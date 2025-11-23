package com.example.citybikeviewer.data

import android.util.Log
import com.example.citybikeviewer.data.local.FavoriteDao
import com.example.citybikeviewer.data.local.FavoriteNetwork
import com.example.citybikeviewer.data.model.Location
import com.example.citybikeviewer.data.model.Network
import com.example.citybikeviewer.data.remote.CityBikeApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CityBikeRepository @Inject constructor(
    private val api: CityBikeApi,
    private val dao: FavoriteDao
) {
    // API Call
    suspend fun getNetworks(): List<Network> {

//        return api.getNetworks().networks

//         Return a fake list to test the UI and Database
        return listOf(
            Network(
                id = "velib",
                name = "Velib (FAKE DATA)",
                location = Location(
                    city = "Paris",
                    country = "FR",
                    latitude = 48.8566,
                    longitude = 2.3522
                ),
                href = "/v2/networks/velib"
            ),
            Network(
                id = "santander",
                name = "Santander Cycles",
                location = Location(
                    city = "London",
                    country = "UK",
                    latitude = 51.5074,
                    longitude = -0.1278
                ),
                href = "/v2/networks/santander-cycles"
            ),
            Network(
                id = "velo-antwerpen",
                name = "Velo Antwerpen",
                location = Location(
                    city = "Antwerp",
                    country = "BE",
                    latitude = 51.2194,
                    longitude = 4.4025
                ),
                href = "/v2/networks/velo-antwerpen"
            )
        )
    }

    fun getFavorites(): Flow<List<FavoriteNetwork>> = dao.getAllFavorites()

    suspend fun addToFavorites(network: Network) {
        val favorite = FavoriteNetwork(
            id = network.id,
            name = network.name,
            city = network.location.city,
            country = network.location.country
        )
        dao.insert(favorite)
    }

    suspend fun removeFromFavorites(networkId: String, name: String, city: String, country: String) {
        val toDelete = FavoriteNetwork(networkId, name, city, country)
        dao.delete(toDelete)
    }

    fun isFavorite(id: String): Flow<Boolean> = dao.isFavorite(id)
}