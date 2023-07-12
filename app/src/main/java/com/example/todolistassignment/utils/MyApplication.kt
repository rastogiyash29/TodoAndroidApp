package com.example.androidapiproject.utils

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MyApplicationContextSingleton.setApplicationContext(applicationContext)

        //We will add those tasks here who need to be executed on app run
    }
}
