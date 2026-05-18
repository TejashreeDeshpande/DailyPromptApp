package com.example.feature.formvalidation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.formvalidation.FormValidationScreen

const val ROUTE_FORM_VALIDATION = "form_validation"

fun NavGraphBuilder.formValidationScreen(navController: NavController) {
    composable(route = ROUTE_FORM_VALIDATION) {
        FormValidationScreen (onBack = { navController.popBackStack() })
    }
}