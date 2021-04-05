package com.diegoribeiro.taskwars

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.diegoribeiro.taskwars.model.Task
import com.diegoribeiro.taskwars.presentation.viewmodel.FormViewModel
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_task.*

class FormActivity : BaseActivity() {
    companion object{
        const val EXTRA_TASK: String = "taskid"
    }

    private lateinit var formViewModel: FormViewModel
    private var myTaskId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        formViewModel = ViewModelProvider.NewInstanceFactory().create(FormViewModel::class.java)

        setupToolbar(toolBarForm, R.string.toolBarFormTitle, true)

        loadFromActivity()
        initObserver()
        btnSave.setOnClickListener {
            saveTask()
            finish()
        }
    }


    private fun loadFromActivity(){
        val bundle = intent.extras
        if (bundle != null){
            myTaskId = bundle.getLong(EXTRA_TASK)
            formViewModel.getOne(myTaskId)
            Log.d("**receiving", "id = $myTaskId")
            if (myTaskId != 0L){
                btnSave.setText(R.string.update_task)
            }
        }
    }

    private fun saveTask(){
        progressForm.visibility = View.VISIBLE
            val id = myTaskId
            val title = editTitle.text.toString()
            val description = editDescription.text.toString()
            val task = Task(id, title, description)
            Log.d("**Saving", "id = $myTaskId")
            formViewModel.saveTask(task)
            progressForm.visibility = View.GONE
    }

    private fun initObserver(){
        formViewModel.validation.observe(this,  {
            if (it.success()){
                Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, it.failure(), Toast.LENGTH_SHORT).show()
            }
        })
        formViewModel.task.observe(this, {
            editTitle.setText(it.title)
            editDescription.setText(it.description)
        })
    }
}




