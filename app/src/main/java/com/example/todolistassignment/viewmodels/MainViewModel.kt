package com.example.todolistassignment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidapiproject.repository.Repository
import com.example.androidapiproject.repository.Repository.getTodo
import com.example.androidapiproject.repository.Repository.updateToDo
import com.example.todolistassignment.models.ToDo
import com.google.gson.Gson
import kotlinx.coroutines.launch


class MainViewModel(private val repository: Repository): ViewModel() {
    // Define the LiveData for the ToDo list
    private val _todoList = MutableLiveData<List<ToDo>>(ArrayList())
    val todoList: LiveData<List<ToDo>> get() = _todoList

    private val _error = MutableLiveData<String>("No Error")
    val error: LiveData<String> get() = _error

    fun fetchTodoList() {
        _error.value="Loading Data"
        viewModelScope.launch {
            try {
                val result = getTodo()
                _todoList.value = result
                _error.value="Data Loaded Successfully"
            } catch (e: Exception) {
                // Handle the exception or error
                _error.value="Some Error occurred please retry"
            }
        }
    }

    fun updateToDo(id:Int,updatedToDo:String){
        _error.value="Updating ToDo"
        viewModelScope.launch {
            try {
                repository.updateToDo(id,updatedToDo)
                fetchTodoList()
                _error.value="Updated Successfully"
            } catch (e: Exception) {
                // Handle the exception or error
                _error.value="Some Error occurred while updating ToDo!! retry"
            }
        }
    }

    fun createToDo(ToDoString:String){
        _error.value="Creating ToDo"
        viewModelScope.launch {
            try {
                repository.createTodo(ToDoString)
                fetchTodoList()
                _error.value="Created Successfully"
            } catch (e: Exception) {
                // Handle the exception or error
                _error.value="Some Error occurred while creating ToDo!! retry"
            }
        }
    }

    fun deleteToDo(id:Int){
        _error.value="Deleting ToDo"
        viewModelScope.launch {
            try {
                repository.deleteTodo(id)
                fetchTodoList()
                _error.value="Deleting Successfully"
            } catch (e: Exception) {
                // Handle the exception or error
                _error.value="Some Error occurred while Deleting ToDo!! retry"
            }
        }
    }
}