package com.example.feature.formvalidation.viewmodel

import android.util.Patterns
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.feature.formvalidation.model.FormUiState

class FormValidationViewModel : ViewModel() {
    var uiState by mutableStateOf(FormUiState())
        private set

    fun updateName(name: String) {
        uiState = uiState.copy(fullName = name)
    }

    fun updateEmail(email: String) {
        uiState = uiState.copy(email = email)
    }

    fun updatePassword(password: String) {
        uiState = uiState.copy(password = password)
    }

    val nameError: String?
        get() = if (uiState.fullName.isBlank()) {
            "Name cannot be empty"
        } else {
            null
        }

    val emailError: String?
        get() = if (
            !Patterns.EMAIL_ADDRESS.matcher(uiState.email).matches()
        ) {
            "Invalid email"
        } else {
            null
        }

    val passwordError: String?
        get() = if (
            uiState.password.length < 8
        ) {
            "Password must be at least 8 characters"
        } else {
            null
        }

    val isFormValid by derivedStateOf {
        nameError == null &&
                emailError == null &&
                passwordError == null
    }

    fun submit() {
        if (isFormValid) {
            uiState = uiState.copy(submitted = true)
        }
    }

}