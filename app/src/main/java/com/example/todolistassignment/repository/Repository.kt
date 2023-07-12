package com.example.androidapiproject.repository

import android.util.Log
import com.example.androidapiproject.api.RetrofitInstance
import com.example.androidapiproject.utils.Functions.Companion.toJsonRequestBody
import com.example.todolistassignment.models.ToDo
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.RequestBody
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object Repository {

    val gson=Gson()

    suspend fun getTodo(): ArrayList<ToDo> {
        return suspendCoroutine { continuation ->
        val newList = ArrayList<ToDo>()
            RetrofitInstance.api.getToDo().enqueue(object : Callback<JsonElement> {
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    if (response.isSuccessful) {
                        val jsonElement: JsonElement? = response.body()
                        jsonElement?.let {
                            when {
                                it.isJsonObject -> {
                                    val todoJsonArray = it.asJsonObject.getAsJsonArray("data")
                                    todoJsonArray.forEach { element ->
                                        val itemToDo = element.asJsonObject
                                        newList.add(
                                            ToDo(
                                                itemToDo.get("id").asInt,
                                                itemToDo.get("todo").asString
                                            )
                                        )
                                    }
                                    continuation.resume(newList)
                                }
                                else -> {
                                    // Handle other types of JSON elements (e.g., primitive values)
                                    continuation.resumeWith(Result.failure(Exception("Received unexpected JSON type received")))
                                }
                            }
                        }
                    } else if(response.code()==404){
                        continuation.resume(newList)
                    }else{
                        // Handle error response
                        continuation.resumeWith(Result.failure(Exception("Error response: ${response.code()}")))
                    }
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    // Handle request failure
                    continuation.resumeWith(Result.failure(t))
                }
            })
        }
    }


    suspend fun updateToDo(id: Int, updatedToDo: String) {
        return suspendCoroutine { continuation ->
            val requestBody = gson.toJson(ToDo(id, updatedToDo)).toString().toJsonRequestBody()
            RetrofitInstance.api.updateToDo(requestBody)
                .enqueue(object : Callback<JsonElement> {
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    if (response.isSuccessful) {
                        continuation.resume(Unit)
                    } else {
                        // Handle error response
                        continuation.resumeWith(Result.failure(Exception("Error response: ${response.code()}")))
                    }
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    // Handle request failure
                    continuation.resumeWith(Result.failure(t))
                }
            })
        }
    }


    private data class newToDo(val todo:String)
    suspend fun createTodo(newTodoString:String){
        return suspendCoroutine { continuation ->
            val requestBody = gson.toJson(newToDo(newTodoString)).toString().toJsonRequestBody()
            RetrofitInstance.api.createToDo(requestBody)
                .enqueue(object : Callback<JsonElement> {
                    override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                        if (response.isSuccessful) {
                            continuation.resume(Unit)
                        } else {
                            // Handle error response
                            continuation.resumeWith(Result.failure(Exception("Error response: ${response.code()}")))
                        }
                    }
                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        // Handle request failure
                        continuation.resumeWith(Result.failure(t))
                    }
                })
        }
    }

    suspend fun deleteTodo(id:Int){
        return suspendCoroutine { continuation ->
            RetrofitInstance.api.deleteToDo(id)
                .enqueue(object : Callback<JsonElement> {
                    override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                        if (response.isSuccessful) {
                            continuation.resume(Unit)
                        } else {
                            // Handle error response
                            continuation.resumeWith(Result.failure(Exception("Error response: ${response.code()}")))
                        }
                    }
                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        // Handle request failure
                        continuation.resumeWith(Result.failure(t))
                    }
                })
        }
    }
    //we can add more get or post functions here w.r.t more api endpoints.
}
