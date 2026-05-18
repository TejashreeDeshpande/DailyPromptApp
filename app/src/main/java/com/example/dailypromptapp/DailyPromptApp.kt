package com.example.dailypromptapp

import android.app.Application
import com.example.feature.counter.di.chatUiModule
import com.example.feature.counter.di.counterModule
import com.example.feature.quiz.di.quizModule
import com.example.feature.pagination.di.paginationModule
import com.example.feature.imagegallery.di.imageGalleryModule
import com.example.feature.leaderboard.di.leaderboardModule
import com.example.feature.search.di.searchModule
import com.example.feature.todolist.di.todolistModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DailyPromptApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DailyPromptApp)

            modules(searchModule)
            modules(counterModule)
            modules(todolistModule)
            modules(chatUiModule)
            modules(imageGalleryModule)
            modules(quizModule)
            modules(paginationModule)
            modules(leaderboardModule)
        }
    }
}
