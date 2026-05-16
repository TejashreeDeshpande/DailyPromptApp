package com.example.feature.counter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.counter.model.Product
import com.example.feature.counter.model.mockProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CounterViewModel : ViewModel() {
    private val _products = MutableStateFlow(mockProducts)
    val products: StateFlow<List<Product>> = _products

    val total: StateFlow<Int> = products
        .map { productList ->
            productList.sumOf { it.quantity }
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun onClickAddProduct(product: Product) {
        _products.value = _products.value.map {
            if (it.id == product.id) {
                it.copy(quantity = it.quantity + 1)
            } else {
                it
            }
        }
    }

    fun onClickRemoveProduct(product: Product) {
        _products.value = _products.value.map {
            if (it.id == product.id && it.quantity > 0) {
                it.copy(quantity = it.quantity - 1)
            } else {
                it
            }
        }
    }
}