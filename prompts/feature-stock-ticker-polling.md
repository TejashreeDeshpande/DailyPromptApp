# Feature Prompt: Live Stock Ticker with Flow Polling

**Difficulty:** Medium-Hard  
**Time limit:** 40 minutes  
**Key concepts:** `flow` builder, `ticker` via `flow + delay`, `zip`, `map`, `conflate`, periodic polling pattern

---

## Goal

Build a stock ticker screen that polls fake prices every 2 seconds, shows price changes with color coding (green = up, red = down), and lets the user pause/resume polling.

---

## Requirements

### Functional
- Display 5 hardcoded stock symbols (e.g. AAPL, GOOGL, MSFT, AMZN, TSLA)
- Every 2 seconds, generate a new "price" for each stock (random walk: ±1–3% from previous)
- Each item shows: symbol, current price, change amount, change % — colored green (up) or red (down)
- A **Pause / Resume** toggle button stops and restarts the polling without resetting prices
- Show a "Last updated: X seconds ago" label that ticks up while paused

### UI
- `LazyColumn` of stock cards
- Each card: symbol (bold), price (large), Δ and Δ% on the right with color
- Pause/Resume FAB or top-bar button
- Timestamp label below the list

---

## ViewModel API to implement

```kotlin
data class StockQuote(
    val symbol: String,
    val price: Double,
    val change: Double,
    val changePct: Double
)

class StockTickerViewModel : ViewModel() {
    val quotes: StateFlow<List<StockQuote>>
    val isPaused: StateFlow<Boolean>
    val secondsSinceUpdate: StateFlow<Int>

    fun togglePause()
}
```

---

## Key implementation detail

```kotlin
// Polling flow — emits every 2 s
private fun tickerFlow(): Flow<Unit> = flow {
    while (true) {
        emit(Unit)
        delay(2_000)
    }
}

// In ViewModel:
private val _isPaused = MutableStateFlow(false)

val quotes: StateFlow<List<StockQuote>> = _isPaused
    .flatMapLatest { paused ->
        if (paused) flowOf(/* current quotes unchanged */)
        else tickerFlow().map { generateNewPrices() }
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), initialQuotes)
```

---

## Hints

- The "random walk" pattern: `newPrice = oldPrice * (1 + Random.nextDouble(-0.03, 0.03))`
- Store the previous prices in the ViewModel so each tick mutates from the last value
- `flatMapLatest` on `_isPaused` lets you completely swap out the active flow when pausing
- For `secondsSinceUpdate`, launch a separate coroutine that increments a counter every second and resets it when `isPaused` changes to false
- `conflate()` can be added to drop ticks if the UI is slow to render

---

## Stretch goals

- Add a sparkline (simple `Canvas`-drawn line chart) per stock showing the last 10 price points
- Let the user tap a stock to see a detail screen with full price history
- Replace random walk with a real API (e.g. Finnhub free tier WebSocket)
