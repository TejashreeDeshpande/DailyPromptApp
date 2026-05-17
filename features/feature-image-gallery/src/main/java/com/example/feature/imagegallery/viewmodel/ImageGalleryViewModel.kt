package com.example.feature.imagegallery.viewmodel

import androidx.lifecycle.ViewModel
import com.example.feature.imagegallery.data.imageUrls
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ImageGalleryViewModel : ViewModel() {

    private val _images = MutableStateFlow(imageUrls)
    val images: StateFlow<List<String>> = _images

}