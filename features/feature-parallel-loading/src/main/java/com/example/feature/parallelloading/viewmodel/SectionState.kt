package com.example.feature.parallelloading.viewmodel

sealed class SectionState<out T> {
    data object Loading : SectionState<Nothing>()

    data class Success<T>(
        val data: T
    ) : SectionState<T>()

    data class Error(
        val message: String
    ) : SectionState<Nothing>()
}