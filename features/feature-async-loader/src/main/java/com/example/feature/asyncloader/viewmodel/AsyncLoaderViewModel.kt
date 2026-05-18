package com.example.feature.asyncloader.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.asyncloader.model.Product
import com.example.feature.asyncloader.model.mockProducts
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

sealed class UiState {
    data class Success(val products: List<Product>) : UiState()
    data class Error(val message: String) : UiState()
    object Loading : UiState()
}

class AsyncLoaderViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            delay(2000)

            val isSuccess = Random.nextBoolean()

            _uiState.value =
                if (isSuccess)
                    UiState.Success(mockProducts)
                else
                    UiState.Error("Failed to load products")
        }
    }
}