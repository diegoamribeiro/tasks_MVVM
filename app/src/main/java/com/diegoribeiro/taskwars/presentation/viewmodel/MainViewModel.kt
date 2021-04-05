package com.diegoribeiro.taskwars.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegoribeiro.taskwars.listeners.ApiListener
import com.diegoribeiro.taskwars.listeners.ValidationListener
import com.diegoribeiro.taskwars.model.Task
import com.diegoribeiro.taskwars.repository.TaskRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel(){

fun init(){
    getAllTasks()
    //getCounter()
}

//    private val mValidation = MutableLiveData<ValidationListener>()
//    val validation: LiveData<ValidationListener> = mValidation

    private val mTaskRepository = TaskRepository()

    private var _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> = _taskList

    private var _counter = MutableLiveData<Long>()
    val counter: LiveData<Long> = _counter

    fun getAllTasks(){
        viewModelScope.launch {
            val response = mTaskRepository.allFromRepository()
            _taskList.postValue(response)
        }
    }

//    private fun getCounter(){
//        viewModelScope.launch {
//            val result = mTaskRepository.counterRepository()
//            _counter.value = result
//            //getAllTasks()
//        }
//    }

    fun deleteTask(id: Long){
        mTaskRepository.delete(id, object : ApiListener<Unit> {
            override fun onSuccess(model: Unit) {
                Log.d("**SUCESSO", "Sucesso")
                getAllTasks()
            }

            override fun onFailure(str: String) {
                Log.d("**FAILED", "Falhou")
            }
        })
    }
}

