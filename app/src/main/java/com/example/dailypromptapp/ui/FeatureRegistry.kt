package com.example.dailypromptapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.LastPage
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.feature.chat.ui.navigation.ROUTE_CHAT
import com.example.feature.counter.navigation.ROUTE_COUNTER
import com.example.feature.imagegallery.navigation.ROUTE_IMAGE_GALLERY
import com.example.feature.leaderboard.navigation.ROUTE_LEADERBOARD
import com.example.feature.pagination.navigation.ROUTE_PAGINATION
import com.example.feature.search.navigation.ROUTE_SEARCH
import com.example.feature.todolist.navigation.ROUTE_TODOLIST
import com.example.feature.quiz.navigation.ROUTE_QUIZ

data class FeatureEntry(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val route: String
)

/**
 * Central registry of available features.
 * Add a new FeatureEntry here whenever you add a new feature module.
 */
val featureRegistry: List<FeatureEntry> = listOf(
    FeatureEntry(
        title = "Search",
        description = "Search for prompts and content",
        icon = Icons.Default.Search,
        route = ROUTE_SEARCH
    ),

    FeatureEntry(
        title = "Counter",
        description = "Counter",
        icon = Icons.Default.Add,
        route = ROUTE_COUNTER
    ),

    FeatureEntry(
        title = "Todolist",
        description = "Todolist",
        icon = Icons.AutoMirrored.Filled.Note,
        route = ROUTE_TODOLIST
    ),

    FeatureEntry(
        title = "Chat",
        description = "Chat",
        icon = Icons.AutoMirrored.Filled.Chat,
        route = ROUTE_CHAT
    ),
    FeatureEntry(
        title = "Image Gallery",
        description = "Image Gallery",
        icon = Icons.Default.AddPhotoAlternate,
        route = ROUTE_IMAGE_GALLERY
    ),
    FeatureEntry(
        title = "Quiz",
        description = "Quiz",
        icon = Icons.AutoMirrored.Filled.Message,
        route = ROUTE_QUIZ
    ),
    FeatureEntry(
        title = "Pagination",
        description = "Pagination",
        icon = Icons.AutoMirrored.Filled.LastPage,
        route = ROUTE_PAGINATION
    ),
    FeatureEntry(
        title = "Leaderboard",
        description = "Leaderboard",
        icon = Icons.Default.Leaderboard,
        route = ROUTE_LEADERBOARD
    )
)
