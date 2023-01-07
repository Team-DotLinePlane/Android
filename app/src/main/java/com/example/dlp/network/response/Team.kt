package com.example.dlp.network.response

data class Team(
    val isAlarmActive: Boolean,
    val mealTime: MealTime,
    val numOfMembers: Int,
    val teamCode: String,
    val teamId: Int,
    val teamName: String
)