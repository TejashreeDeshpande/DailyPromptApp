package com.example.feature.quiz

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature.quiz.model.QModel
import com.example.feature.quiz.viewmodel.QuizViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(onBack: () -> Unit) {
    val viewModel: QuizViewModel = koinViewModel()
    val quiz by viewModel.questions.collectAsStateWithLifecycle()
    val index by viewModel.selectedQuestionIndex.collectAsStateWithLifecycle()

    var showResult by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Quiz") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    )
    { innerPadding ->
        QuizContentScreen(
            modifier = Modifier.padding(innerPadding),
            question = quiz[index],
            onSelectedAnswer = {
                viewModel.updateSelectedAnswer(it)
                showResult = true
            },
            showResult = showResult,
            onClickNext = {
                showResult = false
                viewModel.incrementIndex()
            }
        )
    }
}


@Composable
fun QuizContentScreen(
    modifier: Modifier,
    question: QModel,
    onSelectedAnswer: (String) -> Unit,
    onClickNext: () -> Unit,
    showResult: Boolean = false
) {
    val answers = listOf(
        question.answer1,
        question.answer2,
        question.answer3,
        question.answer4,
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = "Question: ${question.question}",
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        items(answers) { label ->
            Button(
                onClick = { onSelectedAnswer(label) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = label)
            }
        }

        if (showResult) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = if (question.correctAnswer == question.selectedAnswer)
                        "Correct"
                    else
                        "Incorrect",
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                TextButton(
                    onClick = { onClickNext()  }
                ) {
                    Text(text = "Next")
                }
            }
        }
    }
}