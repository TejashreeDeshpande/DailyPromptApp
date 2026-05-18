package com.example.feature.asyncloader.di

import com.example.feature.asyncloader.viewmodel.AsyncLoaderViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val asyncLoaderModule = module {
    viewModel { AsyncLoaderViewModel() }
}