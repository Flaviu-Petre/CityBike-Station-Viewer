package com.example.citybikeviewer.data.remote

import com.example.citybikeviewer.data.model.NetworkResponse
import retrofit2.http.GET

interface CityBikeApi {
    @GET("Flaviu-Petre/e1a6a13d819afae9a92423dc8ea0dadd/raw/416746f7f64280f9fd68e0a59ec5fc2f4a29f1eb/gistfile1.txt")
    suspend fun getNetworks(): NetworkResponse
}