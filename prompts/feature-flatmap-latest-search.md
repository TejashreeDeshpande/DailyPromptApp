# Feature Prompt: Cancellable Search with flatMapLatest

**Difficulty:** Medium-Hard  
**Time limit:** 40 minutes  
**Key concepts:** `flatMapLatest`, structured concurrency, coroutine cancellation, simulated network delay, loading states

---

## Goal

Build a search screen that simulates a network call on every query change, automatically **cancelling** the previous in-flight request when the user types again.

---

## Requirements

### Functional
- A `TextField` for search input
- Each query change triggers a simulated network call (`delay(800)` = fake latency)
- If the user types again before the previous call completes, the old call is cancelled automatically
- Show a loading spinner while the call is in flight
- Show results (a filtered list of 20 hardcoded items) when the call completes
- Show an empty state for no results

### UI
- Search bar at top
- `CircularProgressIndicator` centered while loading
- `LazyColumn` of results when loaded
- "No results for '…'" empty state

---

## ViewModel API to implement

```kotlin
sealed class SearchState {
    data object Idle : SearchState()
    data object Loading : SearchState()
    data class Success(val results: List<String>) : SearchState()
    data class Empty(val query: String) : SearchState()
}

class NetworkSearchViewModel : ViewModel() {
    val query: StateFlow<String>
    val searchState: StateFlow<SearchState>

    fun onQueryChange(q: String)
}
```

---

## Key implementation detail

```kotlin
val searchState: StateFlow<SearchState> = query
    .debounce(300)
    .flatMapLatest { q ->
        if (q.isBlank()) flowOf(SearchState.Idle)
        else flow {
            emit(SearchState.Loading)
            delay(800)   // simulate network
            val results = allItems.filter { it.contains(q, ignoreCase = true) }
            emit(if (results.isEmpty()) SearchState.Empty(q) else SearchState.Success(results))
        }
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SearchState.Idle)
```

---

## Hints

- `flatMapLatest` cancels the previous inner flow when a new value arrives upstream — this is the key operator for "cancel previous network call"
- `debounce(300)` prevents a new search on every single keystroke
- The inner `flow { }` builder is a **cold** flow — a new coroutine is launched for each emission of `query`
- You do **not** need `viewModelScope.launch` explicitly; `stateIn` handles the collection lifecycle

---

## Stretch goals

- Simulate a random network error (throw an exception 20% of the time) and add an `Error` sealed class state with a retry button
- Replace the hardcoded list with a real API call using `Retrofit` + `suspend fun`
- Add `distinctUntilChanged()` before `flatMapLatest` and explain why it matters
