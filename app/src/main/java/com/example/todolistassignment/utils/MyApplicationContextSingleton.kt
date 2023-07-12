package com.example.androidapiproject.utils

import android.content.Context

class MyApplicationContextSingleton {
    companion object {
        private lateinit var appContext: Context

        fun setApplicationContext(context: Context) {
            appContext = context.applicationContext
        }

        fun getApplicationContext(): Context {
            return appContext
        }
    }
}