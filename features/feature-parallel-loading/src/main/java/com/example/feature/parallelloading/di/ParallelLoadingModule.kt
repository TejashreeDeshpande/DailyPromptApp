package com.example.feature.parallelloading.di

import com.example.feature.parallelloading.viewmodel.ParallelLoadingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val parallelLoadingModule = module {
    viewModel { ParallelLoadingViewModel() }
}