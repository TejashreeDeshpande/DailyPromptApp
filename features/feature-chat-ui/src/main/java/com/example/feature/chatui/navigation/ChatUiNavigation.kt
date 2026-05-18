package com.example.feature.chatui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.chatui.ChatScreen

const val ROUTE_CHAT = "chat_screen"

fun NavGraphBuilder.chatScreen(navController: NavController) {
    composable(route = ROUTE_CHAT) {
        ChatScreen (onBack = { navController.popBackStack() })
    }
}
