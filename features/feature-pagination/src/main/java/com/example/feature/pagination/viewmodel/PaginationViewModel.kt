package com.example.feature.pagination.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.pagination.model.User
import com.example.feature.pagination.model.UserMockData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PaginationViewModel : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _currentPage = MutableStateFlow(0)
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val totalPages = UserMockData.TOTAL_PAGES

    fun loadUsers(page: Int) {
        if (_isLoading.value || page > totalPages) return

        viewModelScope.launch {
            delay(500)

            val response = UserMockData.getPage(page)
            val updatedList = if (page == 1) {
                response.users
            } else {
                _users.value + response.users
            }
            _users.value = updatedList
            _currentPage.value = page

            _isLoading.value = false
        }
    }

    fun loadNextPage() {
        val nextPage = _currentPage.value + 1
        loadUsers(nextPage)
    }
}