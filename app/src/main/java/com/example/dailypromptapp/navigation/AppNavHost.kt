package com.example.dailypromptapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dailypromptapp.ui.FeatureListScreen
import com.example.feature.chat.ui.navigation.chatScreen
import com.example.feature.counter.navigation.counterScreen
import com.example.feature.imagegallery.navigation.imageGalleryScreen
import com.example.feature.quiz.navigation.quizScreen
import com.example.feature.search.navigation.searchScreen
import com.example.feature.todolist.navigation.todolistScreen

const val ROUTE_FEATURE_LIST = "feature_list"

/**
 * Root nav host. To wire in a new feature:
 *  1. Add its route extension in its own module (e.g. `fooScreen(navController)`)
 *  2. Call it inside the NavHost block below.
 *  3. Add a FeatureEntry to [com.example.dailypromptapp.ui.featureRegistry].
 */
@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = ROUTE_FEATURE_LIST
    ) {
        composable(ROUTE_FEATURE_LIST) {
            FeatureListScreen(navController = navController)
        }

        // Feature screens — add new feature nav graphs here
        searchScreen(navController)
        counterScreen(navController)
        todolistScreen(navController)
        chatScreen(navController)
        imageGalleryScreen(navController)
        quizScreen(navController)
    }
}
