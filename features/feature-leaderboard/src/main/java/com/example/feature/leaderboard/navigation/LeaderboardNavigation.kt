package com.example.feature.leaderboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.leaderboard.LeaderboardScreen

const val ROUTE_LEADERBOARD = "leaderboard_screen"

fun NavGraphBuilder.leaderboardScreen(navController: NavController) {
    composable(route = ROUTE_LEADERBOARD) {
        LeaderboardScreen(onBack = { navController.popBackStack() })
    }
}
