package com.example.feature.imagegallery.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.imagegallery.ImageGalleryScreen

const val ROUTE_IMAGE_GALLERY = "image_gallery_screen"

fun NavGraphBuilder.imageGalleryScreen(navController: NavController) {
    composable(route = ROUTE_IMAGE_GALLERY) {
        ImageGalleryScreen (onBack = { navController.popBackStack() })
    }
}
