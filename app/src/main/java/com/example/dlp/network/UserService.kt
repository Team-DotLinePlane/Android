package com.example.dlp.network

import com.example.dlp.network.request.ModifyNicknamRequest
import com.example.dlp.network.request.SaveTokenRequest
import com.example.dlp.network.response.GetUerInfoReponse
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("/api/members")
    fun saveToken(
        @Body saveTokenRequest: SaveTokenRequest,
    ): Call<Void>


    @GET("/api/members")
    fun getNicknameByToken(
        @Query("token") token: String,
    ): Call<GetUerInfoReponse>


    @PUT("/api/members")
    fun modifyNickname(
        @Body modifyNicknamRequest: ModifyNicknamRequest,
    ): Call<Void>
}