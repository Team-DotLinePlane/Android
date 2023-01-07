package com.example.dlp.domain.repository

import com.example.dlp.network.response.LocationSearchResponse
import kotlinx.coroutines.flow.Flow

interface MapRepository {

    suspend fun getRestaurant(
        query: String,
        lat: Double,
        lon: Double,
        radius: Int = 5000
    ): Flow<LocationSearchResponse>

}