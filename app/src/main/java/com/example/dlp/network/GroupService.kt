package com.example.dlp.network

import com.example.dlp.network.request.CreateGroupRequest
import com.example.dlp.network.request.JoinGroupRequest
import com.example.dlp.network.response.GetMemberListResponse
import com.example.dlp.network.response.SelectGroupResponse
import com.example.dlp.network.response.Team
import com.example.dlp.network.response.TeamMealTime
import retrofit2.Call
import retrofit2.http.*

interface GroupService {
    @GET("/api/teams")
    fun getGroup(
        @Query("memberToken") memberToken: String,
    ): Call<GetMemberListResponse>

    @POST("/api/teams")
    fun createGroup(
        @Body createGroupRequest: CreateGroupRequest,
    ): Call<TeamMealTime>

    @POST("/api/teams/join")
    fun joinGroup(
        @Body joinGroupRequest: JoinGroupRequest,
    ): Call<Void>

    @GET("/api/teams/{teamId}")
    fun selectGroup(
        @Path("teamId") teamId: Int,
    ): Call<SelectGroupResponse>

}