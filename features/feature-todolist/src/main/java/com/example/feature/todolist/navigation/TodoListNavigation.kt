package com.example.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.todolist.TodolistScreen

const val ROUTE_TODOLIST = "todolist_screen"

fun NavGraphBuilder.todolistScreen(navController: NavController) {
    composable(route = ROUTE_TODOLIST) {
        TodolistScreen(onBack = { navController.popBackStack() })
    }
}
