package com.example.dlp.network.response

data class TeamMealTime(
    val isAlarmActive: Boolean,
    val mealTime: String,
    val numOfMembers: Int,
    val teamCode: String,
    val teamId: Int,
    val teamName: String
)