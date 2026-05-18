package com.example.feature.formvalidation.di

import com.example.feature.formvalidation.viewmodel.FormValidationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val formValidationModule = module {
    viewModel { FormValidationViewModel() }
}