package com.diegoribeiro.taskwars.presentation

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.signature.EmptySignature
import com.diegoribeiro.taskwars.R
import com.diegoribeiro.taskwars.listeners.TaskClickListener
import com.diegoribeiro.taskwars.model.Task
import kotlinx.android.synthetic.main.item_task.view.*

class TaskAdapter(private var myListener: TaskClickListener): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private var taskList: MutableList<Task> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view, taskList, myListener)
    }


    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindView(taskList[position])
        holder.setIsRecyclable(true)
        //setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(taskList[position].id.toInt())
    }


    fun updateList(newTaskList: MutableList<Task>){
        val diffUtil = MyDiffUtil(taskList, newTaskList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        taskList.clear()
        taskList.addAll(newTaskList)
        diffResults.dispatchUpdatesTo(this)

    }


class TaskViewHolder(
    itemView: View,
    private var taskList: List<Task>?,
    private var onItemClickListener: TaskClickListener,
     ): RecyclerView.ViewHolder(itemView) {

    private val textTitle: TextView = itemView.text_title
    private val textDescription: TextView = itemView.text_description

    fun bindView(task: Task) {
        textTitle.text = task.title
        textDescription.text = task.description
    }

    init {
        itemView.setOnClickListener {
            onItemClickListener.onListClick(taskList!![adapterPosition])
        }
        itemView.setOnLongClickListener {
            val taskId = taskList!![adapterPosition]
            onItemClickListener.onDeleteClick(taskId.id)
            Log.d("**AdapterPos", "position ${taskId.id}")

            Log.d("**AdapterPos2", "$adapterPosition")

            return@setOnLongClickListener false
        }
    }
}
}