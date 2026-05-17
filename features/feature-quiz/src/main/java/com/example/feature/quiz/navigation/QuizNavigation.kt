package com.example.feature.quiz.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.quiz.QuizScreen

const val ROUTE_QUIZ = "quiz_screen"

fun NavGraphBuilder.quizScreen(navController: NavController) {
    composable(route = ROUTE_QUIZ) {
        QuizScreen(onBack = { navController.popBackStack() })
    }
}