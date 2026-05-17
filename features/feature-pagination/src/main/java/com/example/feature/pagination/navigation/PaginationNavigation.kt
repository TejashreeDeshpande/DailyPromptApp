package com.example.feature.pagination.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.pagination.PaginationListScreen

const val ROUTE_PAGINATION = "pagination_screen"

fun NavGraphBuilder.paginationScreen(navController: NavController) {
    composable(route = ROUTE_PAGINATION) {
        PaginationListScreen(onBack = { navController.popBackStack() })
    }
}
