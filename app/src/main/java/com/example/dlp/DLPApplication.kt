package com.example.dlp

import android.app.Application
import android.content.Context

class DLPApplication : Application() {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: DLPApplication
        fun ApplicationContext(): Context {
            return instance.applicationContext
        }
    }

}