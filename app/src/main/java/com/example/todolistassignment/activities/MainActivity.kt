package com.example.todolistassignment.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapiproject.repository.Repository
import com.example.todolistassignment.R
import com.example.todolistassignment.adapters.RecyclerViewAdapter
import com.example.todolistassignment.databinding.ActivityMainBinding
import com.example.todolistassignment.models.ToDo
import com.example.todolistassignment.viewmodels.MainViewModel


class MainActivity : AppCompatActivity(),RecyclerViewAdapter.AdapterCallback {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        initialise()
    }

    private fun initialise() {
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerViewAdapter= RecyclerViewAdapter(ArrayList(),this)
        binding.recyclerView.adapter=recyclerViewAdapter

        //setting up mainViewModel
        mainViewModel= MainViewModel(Repository)
        mainViewModel.todoList.observe(this) {
            recyclerViewAdapter.updateData(it)
        }

        mainViewModel.error.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        binding.addButton.setOnClickListener {
            addTodo()
        }
        mainViewModel.fetchTodoList()
    }


    private fun addTodo() {
        val intent = Intent(this, AddTodoActivity::class.java)
        resultLauncherAddTodo.launch(intent)
    }

    private val resultLauncherAddTodo = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val newTodo: String? = data?.getStringExtra("newTodo")
            mainViewModel.createToDo(newTodo!!)
            Toast.makeText(this,"Adding Todo",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,"Addition Cancelled",Toast.LENGTH_SHORT).show()
        }
    }

    private val resultLauncherUpdateTodo = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val updatedTodo: String? = data?.getStringExtra("updatedTodo")
            val todoId:Int?=data?.getIntExtra("todoId",-1)
            mainViewModel.updateToDo(todoId!!,updatedTodo!!)
            Toast.makeText(this,"Updating Todo",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,"Updation Cancelled",Toast.LENGTH_SHORT).show()
        }
    }

    //Adapter Callbacks from RecyclerViewAdapter
    override fun onUpdateToDo(todo: ToDo) {
        val intent = Intent(this, EditTodoActivity::class.java)
        intent.putExtra("todo", todo.todo)
        intent.putExtra("id", todo.id)
        resultLauncherUpdateTodo.launch(intent)
    }

    override fun onDeleteToDo(todo: ToDo) {
        AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Yes") { dialog, which ->
                Toast.makeText(this,"Deleting Todo",Toast.LENGTH_SHORT).show()
                mainViewModel.deleteToDo(todo.id)
            }
            .setNegativeButton("No", null)
            .setCancelable(false)
            .show()
    }
}