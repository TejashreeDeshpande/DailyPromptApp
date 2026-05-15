package com.example.dailypromptapp

import android.app.Application
import com.example.feature.search.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DailyPromptApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DailyPromptApp)
            modules(searchModule)
        }
    }
}
