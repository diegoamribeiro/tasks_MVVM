package com.diegoribeiro.taskwars.repository

import android.util.Log
import com.diegoribeiro.taskwars.R
import com.diegoribeiro.taskwars.listeners.ApiListener
import com.diegoribeiro.taskwars.model.Task
import com.diegoribeiro.taskwars.remote.RetrofitClient
import com.diegoribeiro.taskwars.remote.TaskServices
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TaskRepository() {
    private val mRetrofitClient =  RetrofitClient().createService(TaskServices::class.java)

//    fun allTasks(listener: ApiListener<List<Task>>){
//        val call: Call<List<Task>> = mRetrofitClient.getAllTasks()
//        allFromRepository(call, listener)
//    }

    suspend fun allFromRepository(): List<Task>{
        val list: MutableList<Task> = mutableListOf()
        mRetrofitClient.getAllTasks().isNotEmpty()
        list.addAll(mRetrofitClient.getAllTasks())
        return list
    }


    fun create(task: Task, listener: ApiListener<Boolean>){
        val call: Call<Boolean> = mRetrofitClient.createTask(task.title, task.description)
        call.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.code() != 200){
                    response.body()?.let {
                        val validation = Gson().fromJson(response.errorBody()!!.toString(), String::class.java)
                        listener.onFailure(validation)
                    }
                }else{
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("**Erro!", "Erro ao criar tarefa!")
            }
        })
    }

    fun update(task: Task, listener: ApiListener<Boolean>){
        val call: Call<Boolean> = mRetrofitClient.updateTask(task.id, task.title, task.description)
        call.enqueue(object : Callback<Boolean>{

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFailure("${R.string.erroInesperado}")
            }
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.code() != 200){
                    response.body()?.let {
                        val validation = Gson().fromJson(response.errorBody()!!.toString(), String::class.java)
                        listener.onFailure(validation)
                    }
                }else{
                    response.body()?.let { listener.onSuccess(it) }
                }
            }
        })
    }

//    suspend fun counterRepository(): Long{
//        return mRetrofitClient.counterTask()
//    }

    fun delete(id: Long, listener: ApiListener<Unit>){
        val call: Call<Unit> = mRetrofitClient.deleteTask(id)
        call.enqueue(object : Callback<Unit>{

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                listener.onFailure("Falha na exclusão")
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.code() != 200){
                    val validation = Gson().fromJson(response.errorBody().toString(), String::class.java)
                    response.errorBody()?.let { listener.onFailure(validation) }
                }else{
                    response.body()?.let { listener.onSuccess(it) }
                }
            }
        })
    }

    fun getOne(id: Long, listener: ApiListener<Task>){
        val call: Call<Task> = mRetrofitClient.getOne(id)
        call.enqueue(object : Callback<Task>{
            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                if (response.code() != 200){
                    response.body()?.let {
                        val validation = Gson().fromJson(response.errorBody()!!.toString(), String::class.java)
                        listener.onFailure(validation)
                    }
                }else{
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<Task>, t: Throwable) {
                Log.d("**Erro!", "Erro ao criar tarefa!")
            }
        })
    }
}