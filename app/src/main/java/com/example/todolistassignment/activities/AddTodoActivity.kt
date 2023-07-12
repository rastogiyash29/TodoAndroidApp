package com.example.todolistassignment.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.todolistassignment.R
import com.example.todolistassignment.databinding.AddTodoLayoutBinding
import com.example.todolistassignment.databinding.EditTodoLayoutBinding

class AddTodoActivity : AppCompatActivity() {
    lateinit var binding: AddTodoLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.add_todo_layout)

        initialise()
    }

    private fun initialise() {
        binding.addButton.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("newTodo", binding.editTodoEditText.text.toString())
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        binding.backButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}