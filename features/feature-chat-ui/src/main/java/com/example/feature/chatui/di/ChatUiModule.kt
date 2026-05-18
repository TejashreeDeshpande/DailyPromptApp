package com.example.feature.chatui.di

import com.example.feature.chatui.viewmodel.ChatUiViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val chatUiModule = module {
    viewModel { ChatUiViewModel() }
}