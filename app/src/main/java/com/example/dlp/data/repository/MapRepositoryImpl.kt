package com.example.dlp.data.repository

import com.example.dlp.domain.repository.MapRepository
import com.example.dlp.network.MapService
import com.example.dlp.network.response.LocationSearchResponse
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import retrofit2.Retrofit

class MapRepositoryImpl(
    private val mapService: MapService
) : MapRepository {

    override suspend fun getRestaurant(
        query: String,
        lat: Double,
        lon: Double,
        radius: Int
    ): Flow<LocationSearchResponse> = flow {
        mapService.getRestaurant(
            query = query,
            lat = lat,
            lon = lon,
            radius = radius
        ).suspendOnSuccess {
            emit(data)
        }.suspendOnError {

        }
    }.onStart { }.onCompletion { }

}