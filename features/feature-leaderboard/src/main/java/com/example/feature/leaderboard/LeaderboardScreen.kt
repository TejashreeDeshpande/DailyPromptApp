package com.example.feature.leaderboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature.leaderboard.data.Player
import com.example.feature.leaderboard.viewmodel.LeaderboardViewModel
import org.koin.androidx.compose.koinViewModel
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(onBack: () -> Unit) {
    val viewModel: LeaderboardViewModel = koinViewModel()

    val items by viewModel.players.collectAsStateWithLifecycle()
    val rankedItems by viewModel.rankedPlayers.collectAsStateWithLifecycle()

    var showRank by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Search") },
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
    ) { innerPadding ->
        SearchContent(
            Modifier.padding(innerPadding),
            isFilterActive = showRank,
            players = if (showRank) rankedItems else items,
            onCheckedSwitch = { newValue ->
                showRank = newValue
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(
    modifier: Modifier,
    isFilterActive: Boolean,
    players: List<Player>,
    onCheckedSwitch: (Boolean) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically // Aligns text and switch evenly
        ) {
            Text(
                text = "Show Rankings",
                modifier = Modifier.weight(1f), // Pushes the switch to the far right
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = isFilterActive,
                onCheckedChange = { newValue -> onCheckedSwitch(newValue) }
            )
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(players) { player ->
                ListItem(
                    headlineContent = { Text(text = player.name) },
                    supportingContent = { Text(text = player.score.toString()) },
                    trailingContent = { if (isFilterActive) Text(text = player.rank.toString()) }
                )
            }
        }
    }
}