package com.diegoribeiro.taskwars

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.doOnAttach
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diegoribeiro.taskwars.listeners.TaskClickListener
import com.diegoribeiro.taskwars.model.Task
import com.diegoribeiro.taskwars.presentation.TaskAdapter
import com.diegoribeiro.taskwars.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), TaskClickListener {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var recyclerTask: RecyclerView

    private var mAdapter =  TaskAdapter(this)

    companion object{
        const val TASK_ID: String = "taskid"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //progressMain.visibility = View.GONE
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.init()

        bindViews()
        observe()
        initFormActivity()
    }

    override fun onResume() {
        super.onResume()
        observe()
    }

    private fun bindViews(){
        recyclerTask = findViewById(R.id.recycler_task)
        recyclerTask.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerTask.adapter = mAdapter
    }

    private fun observe(){
        mainViewModel.init()
        mainViewModel.taskList.observe(this@MainActivity, Observer {

            mAdapter.updateList(it as MutableList<Task>)

            tv_counter.text = mAdapter.itemCount.toString()
        })
    }


    private fun initFormActivity(){
        fab_add.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(Intent(intent))
        }
    }

    override fun onListClick(task: Task) {
        val intent = Intent(this, FormActivity::class.java)
        intent.putExtra(TASK_ID, task.id)
        startActivity(intent)
    }



    override fun onDeleteClick(id: Long): Unit {
        val dialog = AlertDialog.Builder(this)
        Log.d("**IdTask", id.toString())


            dialog.apply {
                setTitle("Confirmar Exclusão")
                setMessage("Deseja excluir a tarefa?")
                setPositiveButton("Sim"){ _, _->
                    mainViewModel.deleteTask(id)
                    observe()
                    //observeCounter()
                }

                setNegativeButton("Não"){_, _->
                    //Toast.makeText(context, "Não", Toast.LENGTH_SHORT).show()
                }
                create()
                show()
            }

    }
}