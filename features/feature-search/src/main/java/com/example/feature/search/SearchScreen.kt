package com.example.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature.search.data.Country
import com.example.feature.search.viewModel.SearchViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(onBack: () -> Unit) {
    val viewModel: SearchViewModel = koinViewModel()

    val items = viewModel.filteredCountries.collectAsStateWithLifecycle()
    val query = viewModel.searchQuery.collectAsStateWithLifecycle()

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
            query = query.value,
            items = items.value,
            onQueryChanged = { viewModel.onUpdateSearchQuery(it) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(
    modifier: Modifier,
    query: String,
    items: List<Country>,
    onQueryChanged: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { onQueryChanged(it) },
            label = { Text(text = "Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items) { country ->
                ListItem(
                    leadingContent = { Text(text = country.flag, style = androidx.compose.material3.MaterialTheme.typography.headlineMedium) },
                    headlineContent = { Text(text = country.name) },
                    supportingContent = { Text(text = country.capital) }
                )
            }
        }
    }
}