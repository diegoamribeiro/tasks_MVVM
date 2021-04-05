package com.diegoribeiro.taskwars.presentation

import androidx.recyclerview.widget.DiffUtil
import com.diegoribeiro.taskwars.model.Task

class MyDiffUtil(
    private val oldTaskList: MutableList<Task>,
    private val newTaskList: MutableList<Task>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldTaskList.size
    }

    override fun getNewListSize(): Int {
        return newTaskList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTaskList[oldItemPosition].id == newTaskList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldTaskList[oldItemPosition].id != newTaskList[newItemPosition].id ->{
                false
            }
            oldTaskList[oldItemPosition].title != newTaskList[newItemPosition].title ->{
                false
            }
            oldTaskList[oldItemPosition].description != newTaskList[newItemPosition].description ->{
                false
            }else -> true
        }
    }
}