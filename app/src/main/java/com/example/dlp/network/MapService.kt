package com.example.dlp.network

import com.example.dlp.network.response.LocationSearchResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MapService {
    
    @GET("/v2/local/search/keyword.json?")
    suspend fun getRestaurant(
        @Query("query") query: String,
        @Query("y") lat: Double,
        @Query("x") lon: Double,
        @Query("radius") radius: Int = 5000
    ): ApiResponse<LocationSearchResponse>

}