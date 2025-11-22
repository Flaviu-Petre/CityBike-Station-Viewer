package com.example.citybikeviewer.data.model

import com.google.gson.annotations.SerializedName

data class NetworkResponse(
    @SerializedName("networks")
    val networks: List<Network>
)

data class Network(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,

    @SerializedName("location")
    val location: Location,

    @SerializedName("href")
    val href: String
)

data class Location(
    @SerializedName("city")
    val city: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double
)