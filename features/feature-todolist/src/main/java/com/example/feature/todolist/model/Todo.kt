package com.example.feature.todolist.model

data class Todo(
    val id: String,
    val title: String,
    val status: Boolean = false
)