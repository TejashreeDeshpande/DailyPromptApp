package com.example.feature.leaderboard.data

val mockPlayers = listOf(
    Player(id = 1, name = "Alice", score = 980),
    Player(id = 2, name = "Bob", score = 920),
    Player(id = 3, name = "Charlie", score = 920), // Same score → same rank
    Player(id = 4, name = "David", score = 870),
    Player(id = 5, name = "Emma", score = 840),
    Player(id = 6, name = "Frank", score = 840), // Same score → same rank
    Player(id = 7, name = "Grace", score = 790),
    Player(id = 8, name = "Henry", score = 750),
    Player(id = 9, name = "Ivy", score = 700),
    Player(id = 10, name = "Jack", score = 650)
)