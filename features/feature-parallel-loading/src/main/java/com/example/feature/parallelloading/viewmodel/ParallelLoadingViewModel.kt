package com.example.feature.parallelloading.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.parallelloading.data.ActivityItem
import com.example.feature.parallelloading.data.Stats
import com.example.feature.parallelloading.repository.DashboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class ParallelLoadingViewModel(
    private val repository: DashboardRepository =
        DashboardRepository()
) : ViewModel() {

    private val _stats =
        MutableStateFlow<SectionState<Stats>>(
            SectionState.Loading
        )
    val stats: StateFlow<SectionState<Stats>>
            = _stats.asStateFlow()

    private val _activity =
        MutableStateFlow<SectionState<List<ActivityItem>>>(
            SectionState.Loading
        )
    val activity: StateFlow<SectionState<List<ActivityItem>>>
            = _activity.asStateFlow()

    private val _announcements =
        MutableStateFlow<SectionState<List<String>>>(
            SectionState.Loading
        )
    val announcements:
            StateFlow<SectionState<List<String>>>
            = _announcements.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {

            supervisorScope {
                launch { loadStats() }
                launch { loadActivity() }
                launch { loadAnnouncements() }
            }
        }
    }

    fun retryStats() {
        viewModelScope.launch {
            loadStats()
        }
    }

    fun retryActivity() {
        viewModelScope.launch {
            loadActivity()
        }
    }

    fun retryAnnouncements() {
        viewModelScope.launch {
            loadAnnouncements()
        }
    }

    private suspend fun loadStats() {
        _stats.value = SectionState.Loading

        _stats.value = runCatching {
            repository.getStats()
        }.fold(
            onSuccess = {
                SectionState.Success(it)
            },
            onFailure = {
                SectionState.Error(
                    it.message ?: "Unknown error"
                )
            }
        )
    }

    private suspend fun loadActivity() {
        _activity.value = SectionState.Loading

        _activity.value = runCatching {
            repository.getActivity()
        }.fold(
            onSuccess = {
                SectionState.Success(it)
            },
            onFailure = {
                SectionState.Error(
                    it.message ?: "Unknown error"
                )
            }
        )
    }

    private suspend fun loadAnnouncements() {
        _announcements.value =
            SectionState.Loading

        _announcements.value = runCatching {
            repository.getAnnoucements()
        }.fold(
            onSuccess = {
                SectionState.Success(it)
            },
            onFailure = {
                SectionState.Error(
                    it.message ?: "Unknown error"
                )
            }
        )
    }
}