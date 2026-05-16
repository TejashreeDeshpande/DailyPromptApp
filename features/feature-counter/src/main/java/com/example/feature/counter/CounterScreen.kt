package com.example.feature.counter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature.counter.model.Product
import com.example.feature.counter.viewmodel.CounterViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterListScreen(onBack: () -> Unit) {

    val viewModel: CounterViewModel = koinViewModel()
    val items = viewModel.products.collectAsStateWithLifecycle()
    val total = viewModel.total.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Counter") },
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
        CounterContentScreen(
            modifier = Modifier.padding(innerPadding),
            products = items.value,
            total = total.value,
            onClickAdd = { viewModel.onClickAddProduct(it) },
            onClickRemove = { viewModel.onClickRemoveProduct(it) }
        )
    }
}


@Composable
fun CounterContentScreen(
    modifier: Modifier,
    products: List<Product>,
    total: Int,
    onClickAdd: (Product) -> Unit,
    onClickRemove: (Product) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End

        ) {
            Text(text = "Selected Items $total")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(products) { product ->
                ListItem(
                    leadingContent = { Text(text = product.icon) },
                    headlineContent = { Text(text = product.name) },
                    supportingContent = { Text(text = product.quantity.toString()) },
                    trailingContent = {
                        Row {
                            IconButton(onClick = {
                                onClickRemove(product)
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Remove,
                                    contentDescription = "Remove",
                                )
                            }
                            IconButton(onClick = {
                                onClickAdd(product)
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Add",
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}