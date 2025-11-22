package com.example.citybikeviewer.data.remote

import com.example.citybikeviewer.data.model.NetworkResponse
import retrofit2.http.GET

interface CityBikeApi {
    @GET("v2/networks")
    suspend fun getNetworks(): NetworkResponse
}