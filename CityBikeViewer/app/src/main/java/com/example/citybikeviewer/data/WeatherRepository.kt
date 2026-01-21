package com.example.citybikeviewer.data

import com.example.citybikeviewer.data.model.WeatherResponse
import com.example.citybikeviewer.data.remote.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi
) {
    private val API_KEY = "YOUR_API_KEY_HERE"

    suspend fun getWeather(lat: Double, lon: Double): WeatherResponse {
        val query = "$lat,$lon"
        return api.getCurrentWeather(apiKey = API_KEY, query = query)
    }
}