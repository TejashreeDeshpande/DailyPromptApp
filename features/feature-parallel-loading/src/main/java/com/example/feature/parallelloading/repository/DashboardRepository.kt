package com.example.feature.parallelloading.repository

import com.example.feature.parallelloading.data.Stats
import com.example.feature.parallelloading.data.ActivityItem
import kotlinx.coroutines.delay
import java.io.IOException
import kotlin.random.Random

class DashboardRepository() {
    suspend fun getStats(): Stats {
        delay(600)
        maybeFail()

        return Stats(
            users = 1500,
            revenue = 24500,
            growth = 18
        )
    }

    suspend fun getActivity(): List<ActivityItem> {
        delay(1_200)
        maybeFail()

        return List(5) {
            ActivityItem(
                id =  it,
                title = "Acivity $it"
            )
        }
    }

    suspend fun getAnnoucements(): List<String> {
        delay(800)
        maybeFail()

        return listOf(
            "Summer Sale Live",
            "App Update Available"
        )
    }

    private fun maybeFail() {
        if (Random.nextFloat() < 0.2f) {
            throw IOException("Network Error")
        }
    }
}