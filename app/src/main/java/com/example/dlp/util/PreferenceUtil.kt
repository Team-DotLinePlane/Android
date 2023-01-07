package com.example.dlp.util

import android.content.Context
import android.content.SharedPreferences
import com.example.dlp.DLPApplication.Companion.ApplicationContext

object PreferenceUtil {

    const val FCM_TOKEN_TEXT = "FCM_TOKEN"
    const val EMPTY_TEXT = "EMPTY"

    private val prefs: SharedPreferences =
        ApplicationContext().getSharedPreferences(FCM_TOKEN_TEXT, Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

}