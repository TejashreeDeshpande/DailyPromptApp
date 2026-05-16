package com.example.feature.counter.di

import com.example.feature.counter.viewmodel.CounterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val counterModule = module {
    viewModel { CounterViewModel() }
}