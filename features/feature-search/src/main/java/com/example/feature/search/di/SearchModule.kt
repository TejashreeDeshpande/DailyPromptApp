package com.example.feature.search.di

import com.example.feature.search.viewModel.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    viewModel { SearchViewModel() }
}
