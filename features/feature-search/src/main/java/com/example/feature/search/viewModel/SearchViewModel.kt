package com.example.feature.search.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.search.data.mockCountries
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SearchViewModel : ViewModel() {

    private val _countries = MutableStateFlow(mockCountries)

    val searchQuery = MutableStateFlow("")

    val filteredCountries = searchQuery
        .combine(_countries) { query, countries ->
            if (query.isBlank()) {
                countries
            } else {
                countries.filter {
                    it.name.contains(query, ignoreCase = true) ||
                    it.capital.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _countries.value
        )

    fun onUpdateSearchQuery(text: String) {
        searchQuery.value = text
    }
}