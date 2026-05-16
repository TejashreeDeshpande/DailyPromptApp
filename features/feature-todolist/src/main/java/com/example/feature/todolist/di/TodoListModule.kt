package com.example.feature.todolist.di

import com.example.feature.todolist.viewmodel.TodolistViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

val todolistModule = module {
    viewModel { TodolistViewModel() }
}