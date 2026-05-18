package com.example.feature.leaderboard.data

data class Player(
    val id: Int,
    val name: String,
    val score: Int,
    val rank: Int = -1
)
