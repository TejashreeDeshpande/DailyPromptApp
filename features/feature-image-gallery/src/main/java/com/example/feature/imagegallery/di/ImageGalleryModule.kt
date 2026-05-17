package com.example.feature.imagegallery.di

import com.example.feature.imagegallery.viewmodel.ImageGalleryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val imageGalleryModule = module {
    viewModel { ImageGalleryViewModel() }
}
