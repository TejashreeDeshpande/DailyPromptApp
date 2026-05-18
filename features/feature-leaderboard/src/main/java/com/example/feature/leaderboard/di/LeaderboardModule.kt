package com.example.feature.leaderboard.di

import com.example.feature.leaderboard.viewmodel.LeaderboardViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val leaderboardModule = module {
    viewModel { LeaderboardViewModel() }
}
