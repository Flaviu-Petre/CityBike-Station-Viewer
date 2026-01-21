package com.example.citybikeviewer.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("current")
    val current: CurrentWeather
)

data class CurrentWeather(
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("condition")
    val condition: WeatherCondition,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("wind_kph")
    val windKph: Double
)

data class WeatherCondition(
    @SerializedName("text")
    val text: String,
    @SerializedName("icon")
    val icon: String
)