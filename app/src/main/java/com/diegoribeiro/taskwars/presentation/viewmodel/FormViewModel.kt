package com.diegoribeiro.taskwars.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diegoribeiro.taskwars.listeners.ApiListener
import com.diegoribeiro.taskwars.listeners.ValidationListener
import com.diegoribeiro.taskwars.model.Task
import com.diegoribeiro.taskwars.repository.TaskRepository

class FormViewModel : ViewModel() {

    private val mRepository = TaskRepository()

    private var _validation = MutableLiveData<ValidationListener>()
    val validation: LiveData<ValidationListener> = _validation

    private var _task = MutableLiveData<Task>()
    val task: LiveData<Task> = _task



    fun saveTask(task: Task){
        if (task.id == 0L){
            mRepository.create(task, object :  ApiListener<Boolean>{
                override fun onSuccess(model: Boolean) {
                    _validation.value = ValidationListener()
                }

                override fun onFailure(str: String) {
                    _validation.value = ValidationListener(str)
                }
            })
        }else{
            mRepository.update(task, object :  ApiListener<Boolean>{
                override fun onSuccess(model: Boolean) {
                    _validation.value = ValidationListener()
                }

                override fun onFailure(str: String) {
                    _validation.value = ValidationListener(str)
                }
            })
        }
    }

    fun getOne(taskId: Long){
        mRepository.getOne(taskId, object: ApiListener<Task>{
            override fun onSuccess(model: Task) {
                _task.value = model
            }
            override fun onFailure(str: String) {
            }
        })
    }

}