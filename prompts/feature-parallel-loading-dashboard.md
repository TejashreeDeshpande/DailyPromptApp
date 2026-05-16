# Feature Prompt: Dashboard with Parallel Data Loading

**Difficulty:** Medium-Hard  
**Time limit:** 40 minutes  
**Key concepts:** `async`/`await`, `coroutineScope`, parallel vs sequential loading, `supervisorScope`, error handling per section

---

## Goal

Build a dashboard screen that loads three independent data sections in **parallel** and renders each as it arrives, rather than waiting for all three to finish sequentially.

---

## Requirements

### Functional
- Three dashboard sections, each loaded independently:
  1. **Stats** — 3 numeric KPIs (simulated: `delay(600)`)
  2. **Recent Activity** — list of 5 items (simulated: `delay(1_200)`)
  3. **Announcements** — list of 2 banners (simulated: `delay(800)`)
- All three requests fire at the **same time** (parallel, not sequential)
- Each section shows its own `CircularProgressIndicator` until its data arrives
- If one section fails (simulate 20% error rate), show a "Retry" button in that section only — the other sections are unaffected

### UI
- `LazyColumn` with three section headers
- Each section independently transitions from loading → content or error state
- Pull-to-refresh triggers all three sections to reload in parallel

---

## ViewModel API to implement

```kotlin
sealed class SectionState<out T> {
    data object Loading : SectionState<Nothing>()
    data class Success<T>(val data: T) : SectionState<T>()
    data class Error(val message: String) : SectionState<Nothing>()
}

class DashboardViewModel : ViewModel() {
    val stats: StateFlow<SectionState<Stats>>
    val activity: StateFlow<SectionState<List<ActivityItem>>>
    val announcements: StateFlow<SectionState<List<String>>>

    fun refresh()
    fun retryStats()
    fun retryActivity()
    fun retryAnnouncements()
}
```

---

## Key implementation detail

```kotlin
fun refresh() {
    viewModelScope.launch {
        // Fire all three in parallel — supervisorScope so one failure doesn't cancel others
        supervisorScope {
            launch { loadStats() }
            launch { loadActivity() }
            launch { loadAnnouncements() }
        }
    }
}

private suspend fun loadStats() {
    _stats.value = SectionState.Loading
    _stats.value = try {
        delay(600)
        if (Random.nextFloat() < 0.2f) throw IOException("Network error")
        SectionState.Success(fakeStats())
    } catch (e: Exception) {
        SectionState.Error(e.message ?: "Unknown error")
    }
}
```

---

## Hints

- `supervisorScope` vs `coroutineScope`: in `coroutineScope`, a child failure cancels all siblings. `supervisorScope` isolates failures — essential for independent sections
- `async { } + await()` is useful when you need the **result** of parallel work; `launch` is fine when each coroutine updates state itself
- Sequential loading would take 600 + 1200 + 800 = 2600 ms; parallel takes max(600, 1200, 800) = 1200 ms
- `SwipeRefresh` / `PullRefreshIndicator` can track a derived `isRefreshing: Boolean` = any section is `Loading`

---

## Stretch goals

- Use `async`/`awaitAll` to combine all results into a single emission and measure the time saved
- Add a global error state if **all three** sections fail
- Cache the last successful result per section so a retry failure shows stale data instead of an empty error card
