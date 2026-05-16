package com.example.feature.counter.di

import com.example.feature.counter.viewmodel.ChatUiViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val chatUiModule = module {
    viewModel { ChatUiViewModel() }
}