package com.diegoribeiro.taskwars.listeners

import com.diegoribeiro.taskwars.model.Task

interface TaskClickListener {

    fun onListClick(task: Task)


    fun onDeleteClick(id: Long): Unit

}