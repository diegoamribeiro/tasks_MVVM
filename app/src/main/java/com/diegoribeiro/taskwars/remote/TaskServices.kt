package com.diegoribeiro.taskwars.remote

import com.diegoribeiro.taskwars.model.Task
import retrofit2.Call
import retrofit2.http.*

interface TaskServices {

    @GET("tasks")
    suspend fun getAllTasks(): List<Task>

//    @GET("tasks/count")
//    suspend fun counterTask(): Long

    @POST("tasks/save")
    @FormUrlEncoded
    fun createTask(@Field("title") title: String, @Field("description") description: String): Call<Boolean>


    @HTTP(method = "PUT", path = "tasks/update/{id}", hasBody = true)
    @FormUrlEncoded
    fun updateTask(
        @Path("id") id: Long,
        @Field("title") title: String,
        @Field("description") description: String
    ): Call<Boolean>


    @DELETE("tasks/{id}")
    fun deleteTask(@Path("id", encoded = true) id: Long): Call<Unit>


    @GET("tasks/{id}")
    fun getOne(@Path(value = "id", encoded = true) id: Long): Call<Task>
}