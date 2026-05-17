package com.example.feature.quiz.model

data class QModel(
    val question: String,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val answer4: String,
    val correctAnswer: String,
    val selectedAnswer: String = "",
)