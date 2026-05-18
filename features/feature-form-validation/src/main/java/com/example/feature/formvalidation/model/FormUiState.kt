package com.example.feature.formvalidation.model

data class FormUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val submitted: Boolean = false
)