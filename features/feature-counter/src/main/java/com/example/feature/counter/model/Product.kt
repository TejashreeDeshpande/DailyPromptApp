package com.example.feature.counter.model

data class Product(
    val id: String,
    val name: String,
    val icon: String,
    val quantity: Int = 0
)