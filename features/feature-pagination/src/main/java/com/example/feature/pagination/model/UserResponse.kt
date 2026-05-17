package com.example.feature.pagination.model

data class UserResponse(
    val page: Int,
    val pageSize: Int,
    val totalPages: Int,
    val totalItems: Int,
    val users: List<User>
)