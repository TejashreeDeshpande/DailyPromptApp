package com.example.feature.pagination

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature.pagination.model.User
import com.example.feature.pagination.viewmodel.PaginationViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaginationListScreen(onBack: () -> Unit) {

    val viewModel: PaginationViewModel = koinViewModel()
    val items by viewModel.users.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItems = layoutInfo.totalItemsCount

            lastVisibleItem to totalItems
        }
            .distinctUntilChanged()
            .collect { (lastVisible, total) ->
                if (!isLoading && total > 0 && lastVisible >= total - 2) {
                    viewModel.loadNextPage()
                }
            }
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Users") },
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
        UserListScreenContents(
            modifier = Modifier.padding(innerPadding),
            users = items,
            isLoading = isLoading,
            listState = listState
        )
    }
}

@Composable
fun UserListScreenContents(
    modifier: Modifier,
    users: List<User>,
    isLoading: Boolean,
    listState: LazyListState
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = listState
    ) {
        items(users) { user ->
            ListItem(
                headlineContent = { Text(text = user.name) },
                supportingContent = { Text(text = user.city) },
            )
        }
        item {
            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}