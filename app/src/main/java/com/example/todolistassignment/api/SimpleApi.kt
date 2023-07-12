package com.example.androidapiproject.api

import com.google.gson.JsonElement
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Query

interface SimpleApi {

    @GET("api/todo")
    fun getToDo(): Call<JsonElement>

    @POST("api/todo")
    fun createToDo(@Body requestBody: RequestBody): Call<JsonElement>

    @PUT("api/todo")
    fun updateToDo(@Body requestBody: RequestBody): Call<JsonElement>

    @DELETE("api/todo")
    fun deleteToDo(@Query("todo_id") todoId: Int): Call<JsonElement>

    // We can Add more API methods here
}

