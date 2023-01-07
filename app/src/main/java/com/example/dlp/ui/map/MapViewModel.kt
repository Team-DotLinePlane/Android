package com.example.dlp.ui.map

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dlp.DLPApplication.Companion.ApplicationContext
import com.example.dlp.data.repository.MapRepositoryImpl
import com.example.dlp.network.NetworkModule
import com.example.dlp.network.response.Marker
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(ApplicationContext())

    private val locationRequest: LocationRequest
    private val myLocationCallback: MyLocationCallback

    private val mapRepository = MapRepositoryImpl(NetworkModule.mapService)

    private val _categoryName: MutableLiveData<String> = MutableLiveData("돼지고기")
    val categoryName: LiveData<String> get() = _categoryName

    private val _markers: MutableLiveData<List<Marker>> = MutableLiveData(emptyList())
    val markers: LiveData<List<Marker>> get() = _markers

    private val _userLoaction: MutableLiveData<Location?> = MutableLiveData(null)
    val userLocation: LiveData<Location?> get() = _userLoaction

    init {
        locationRequest =
            LocationRequest.Builder(Long.MAX_VALUE) // 초기 1회만 가져옴
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setMinUpdateIntervalMillis(3000)
                .build()
        myLocationCallback = MyLocationCallback()
    }

    fun getRestaurant(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            mapRepository.getRestaurant(
                query = categoryName.value ?: "",
                lat = latitude,
                lon = longitude,
                radius = SEARCH_RADIUS
            ).collectLatest {
                _markers.value = it.markers
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun addLocationListener() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            myLocationCallback,
            Looper.getMainLooper()
        )
    }

    fun removeLocationListener() {
        fusedLocationClient.removeLocationUpdates(myLocationCallback)
    }

    inner class MyLocationCallback : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val location = locationResult.lastLocation
            _userLoaction.value = location
        }
    }

    companion object {
        const val SEARCH_RADIUS = 5000
    }

}