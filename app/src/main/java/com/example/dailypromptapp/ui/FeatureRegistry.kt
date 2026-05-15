package com.example.dailypromptapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.feature.search.navigation.ROUTE_SEARCH

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
    )
)
