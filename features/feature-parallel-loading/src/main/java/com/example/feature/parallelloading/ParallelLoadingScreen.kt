package com.example.feature.parallelloading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feature.parallelloading.data.ActivityItem
import com.example.feature.parallelloading.data.Stats
import com.example.feature.parallelloading.viewmodel.ParallelLoadingViewModel
import com.example.feature.parallelloading.viewmodel.SectionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParallelLoadingScreen(onBack: () -> Unit) {

    val viewModel: ParallelLoadingViewModel = viewModel()

    val stats by viewModel.stats.collectAsState()
    val activity by viewModel.activity.collectAsState()
    val announcements by viewModel
        .announcements
        .collectAsState()

    val isRefreshing =
        stats is SectionState.Loading ||
                activity is SectionState.Loading ||
                announcements is SectionState.Loading

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

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {

            // Refresh Button
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            viewModel.refresh()
                        },
                        enabled = !isRefreshing
                    ) {

                        if (isRefreshing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp
                            )

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )
                        }

                        Text(
                            text = if (isRefreshing) {
                                "Refreshing..."
                            } else {
                                "Refresh"
                            }
                        )
                    }
                }
            }

            // Stats
            item {
                SectionHeader("Stats")
            }

            item {
                StatsSection(
                    state = stats,
                    onRetry = {
                        viewModel.retryStats()
                    }
                )
            }

            // Activity
            item {
                SectionHeader("Recent Activity")
            }

            item {
                ActivitySection(
                    state = activity,
                    onRetry = {
                        viewModel.retryActivity()
                    }
                )
            }

            // Announcements
            item {
                SectionHeader("Announcements")
            }

            item {
                AnnouncementSection(
                    state = announcements,
                    onRetry = {
                        viewModel.retryAnnouncements()
                    }
                )
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun StatsSection(
    state: SectionState<Stats>,
    onRetry: () -> Unit
) {
    when (state) {

        SectionState.Loading -> {
            CircularProgressIndicator()
        }

        is SectionState.Success -> {
            Column {
                Text("Users: ${state.data.users}")
                Text("Revenue: ${state.data.revenue}")
                Text("Growth: ${state.data.growth}%")
            }
        }

        is SectionState.Error -> {
            ErrorSection(
                message = state.message,
                onRetry = onRetry
            )
        }
    }
}

@Composable
fun ActivitySection(
    state: SectionState<List<ActivityItem>>,
    onRetry: () -> Unit
) {
    when (state) {

        SectionState.Loading -> {
            CircularProgressIndicator()
        }

        is SectionState.Success -> {
            Column {
                state.data.forEach {
                    Text(it.title)
                }
            }
        }

        is SectionState.Error -> {
            ErrorSection(
                message = state.message,
                onRetry = onRetry
            )
        }
    }
}

@Composable
fun AnnouncementSection(
    state: SectionState<List<String>>,
    onRetry: () -> Unit
) {
    when (state) {

        SectionState.Loading -> {
            CircularProgressIndicator()
        }

        is SectionState.Success -> {
            Column {
                state.data.forEach {
                    Text(it)
                }
            }
        }

        is SectionState.Error -> {
            ErrorSection(
                message = state.message,
                onRetry = onRetry
            )
        }
    }
}

@Composable
fun ErrorSection(
    message: String,
    onRetry: () -> Unit
) {
    Column {

        Text(message)

        TextButton(
            onClick = onRetry
        ) {
            Text("Retry")
        }
    }
}