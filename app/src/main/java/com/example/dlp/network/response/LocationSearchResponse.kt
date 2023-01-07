package com.example.dlp.network.response

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationSearchResponse(
    @SerializedName("documents")
    val markers: List<Marker>,
    @SerializedName("meta")
    val meta: Meta
)