package com.example.todolistassignment.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.todolistassignment.R
import com.example.todolistassignment.databinding.EditTodoLayoutBinding

class EditTodoActivity : AppCompatActivity() {
    lateinit var binding: EditTodoLayoutBinding
    lateinit var todoText:String
    var todoId:Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.edit_todo_layout)

        initialise()
    }

    private fun initialise() {
                todoText = intent.getStringExtra("todo")!!
                todoId = intent.getIntExtra("id",-1)

                binding.editTodoEditText.setText(todoText)

                binding.editButton.setOnClickListener {
                    val resultIntent = Intent().apply {
                        putExtra("updatedTodo", binding.editTodoEditText.text.toString())
                        putExtra("todoId",todoId)
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