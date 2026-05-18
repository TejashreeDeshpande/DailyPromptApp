package com.example.feature.formvalidation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.parallelloading.ParallelLoadingScreen

const val ROUTE_PARALLEL_LOADING = "parallel_loading"

fun NavGraphBuilder.parallelLoadingScreen(navController: NavController) {
    composable(route = ROUTE_PARALLEL_LOADING) {
        ParallelLoadingScreen(onBack = { navController.popBackStack() })
    }
}
