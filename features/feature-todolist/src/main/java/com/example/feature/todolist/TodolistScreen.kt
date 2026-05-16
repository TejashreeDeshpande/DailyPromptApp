package com.example.feature.todolist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature.todolist.model.Todo
import com.example.feature.todolist.viewmodel.TodolistViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.collections.emptyList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodolistScreen(onBack: () -> Unit) {
    val viewModel: TodolistViewModel = koinViewModel()

    val list by viewModel.list.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Todo List") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            IconButton(onClick = {
                showDialog = true
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.NoteAdd,
                    contentDescription = "Add Note"
                )
            }
        }
    )
    { innerPadding ->
        TodolistContents(
            modifier = Modifier.padding(innerPadding),
            list = list,
            onCheckedChange = { viewModel.updateStatus(it) }
        )

        AddTextDialog(
            showDialog = showDialog,

            onDismiss = {
                showDialog = false
            },

            onDone = {
                viewModel.addTodo(
                    todo = Todo(
                        id = System.currentTimeMillis().toString(),
                        title = it
                    )
                )
                showDialog = false
            }
        )
    }
}

@Composable
fun TodolistContents(
    modifier: Modifier,
    list: List<Todo>,
    onCheckedChange: (Todo) -> Unit
) {

    LazyColumn(modifier = modifier) {
        items(list) { item ->
            ListItem(
                headlineContent = { Text(text = item.title) },
                trailingContent = {
                    Checkbox(checked = item.status, onCheckedChange = { onCheckedChange(item) })
                }
            )
        }
    }
}

@Composable
fun AddTextDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDone: (String) -> Unit
) {

    if (showDialog) {

        var text by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = onDismiss,

            title = {
                Text("Add Text")
            },

            text = {

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = {
                        Text("Enter text")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        onDone(text)
                    }
                ) {
                    Text("Done")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = onDismiss
                ) {
                    Text("Close")
                }
            }
        )
    }
}