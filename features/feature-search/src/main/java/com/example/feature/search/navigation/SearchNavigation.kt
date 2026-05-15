package com.example.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.search.SearchScreen

const val ROUTE_SEARCH = "search_screen"

fun NavGraphBuilder.searchScreen(navController: NavController) {
    composable(route = ROUTE_SEARCH) {
        SearchScreen(onBack = { navController.popBackStack() })
    }
}
