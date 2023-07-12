package com.example.androidapiproject.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class Functions {
    companion object{
        //Create useful utility functions here

        fun String.toJsonRequestBody(): RequestBody {
            return this.toRequestBody("application/json".toMediaTypeOrNull())
        }
    }
}