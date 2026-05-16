package com.example.feature.counter.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.counter.CounterListScreen

const val ROUTE_COUNTER = "counter_screen"

fun NavGraphBuilder.counterScreen(navController: NavController) {
    composable(route = ROUTE_COUNTER) {
        CounterListScreen(onBack = { navController.popBackStack() })
    }
}
