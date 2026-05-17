package com.example.feature.pagination.di

import com.example.feature.pagination.viewmodel.PaginationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val paginationModule = module {
    viewModel { PaginationViewModel() }
}