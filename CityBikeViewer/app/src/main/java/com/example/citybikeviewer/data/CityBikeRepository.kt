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
        return api.getNetworks().networks
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

//    fun isFavorite(id: String): Flow<Boolean> = dao.isFavorite(id)
}