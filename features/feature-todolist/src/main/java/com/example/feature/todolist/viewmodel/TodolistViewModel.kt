package com.example.feature.todolist.viewmodel

import androidx.lifecycle.ViewModel
import com.example.feature.todolist.model.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.collections.emptyList

class TodolistViewModel : ViewModel() {
    private val _list = MutableStateFlow<List<Todo>>(emptyList())
    val list: StateFlow<List<Todo>> = _list

    fun addTodo(todo: Todo) {
        _list.value += todo
    }

    fun updateStatus(todo: Todo) {
        _list.value = _list.value.map {
            if (it.id == todo.id) {
                val s = it.status
                it.copy(status = !s)
            } else {
                it
            }
        }
    }

    fun deleteTodo(todo: Todo) {
        _list.value -= todo
    }
}