package com.example.feature.asyncloader.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.asyncloader.AsyncLoaderScreen

const val ROUTE_ASYNC_LOADER = "async_loader_screen"

fun NavGraphBuilder.asyncLoaderScreen(navController: NavController) {
    composable(route = ROUTE_ASYNC_LOADER) {
        AsyncLoaderScreen(onBack = { navController.popBackStack() })
    }
}