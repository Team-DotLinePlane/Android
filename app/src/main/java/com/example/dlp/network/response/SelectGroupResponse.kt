package com.example.dlp.network.response

data class SelectGroupResponse(
    val isAlarmActive: Boolean,
    val mealTime: String,
    val memberResponses: List<MemberResponse>,
    val teamCode: String,
    val teamId: Int,
    val teamName: String
)