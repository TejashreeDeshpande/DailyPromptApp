package com.example.feature.quiz.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.feature.quiz.model.QModel
import com.example.feature.quiz.model.mockQuestions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter

class QuizViewModel() : ViewModel() {
    private val _questions = MutableStateFlow<List<QModel>>(mockQuestions)
    val questions: StateFlow<List<QModel>> = _questions

    private val _selectedQuestionIndex = MutableStateFlow(0)
    val selectedQuestionIndex: StateFlow<Int> = _selectedQuestionIndex.asStateFlow()

    fun updateSelectedAnswer(answer: String) {
        _questions.value = _questions.value.mapIndexed { index, question ->
            question.takeIf { index != selectedQuestionIndex.value }
                ?: question.copy(selectedAnswer = answer)
        }
    }

    fun incrementIndex() {
        _selectedQuestionIndex.value += 1
    }
}