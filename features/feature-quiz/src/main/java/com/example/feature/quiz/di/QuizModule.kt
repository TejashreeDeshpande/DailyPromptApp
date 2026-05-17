package com.example.feature.quiz.di


import com.example.feature.quiz.viewmodel.QuizViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val quizModule = module {
    viewModel { QuizViewModel() }
}