package com.example.citybikeviewer.data

import com.example.citybikeviewer.data.model.Network
import com.example.citybikeviewer.data.remote.CityBikeApi
import javax.inject.Inject

class CityBikeRepository @Inject constructor(
    private val api: CityBikeApi
) {
    suspend fun getNetworks(): List<Network> {
        return api.getNetworks().networks
    }
}