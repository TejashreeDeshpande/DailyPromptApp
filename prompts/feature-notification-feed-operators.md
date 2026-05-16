# Feature Prompt: Notification Feed with Flow Operators

**Difficulty:** Hard  
**Time limit:** 40 minutes  
**Key concepts:** `buffer`, `conflate`, `transform`, `scan`, `distinctUntilChanged`, `take`, `zip`, chaining operators

---

## Goal

Build a notification feed that simulates a rapid stream of incoming notifications, applying Flow operators to throttle, deduplicate, and accumulate them before rendering.

---

## Requirements

### Functional
- A background flow emits a new fake notification every 300 ms (simulates a firehose)
- The UI should **not** re-render on every emission — batch updates every 1 second
- Deduplicate notifications with the same `id` (can arrive multiple times)
- Show a running total count ("X notifications received")
- Allow marking all as read (clears the unread badge count)
- A **Pause / Resume** button stops new notifications arriving

### UI
- `LazyColumn` of notification cards (newest first)
- Unread badge count in the top bar
- "All caught up!" empty state
- Pause / Resume button in the top bar

---

## ViewModel API to implement

```kotlin
data class Notification(
    val id: Int,
    val title: String,
    val body: String,
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

class NotificationFeedViewModel : ViewModel() {
    val notifications: StateFlow<List<Notification>>
    val unreadCount: StateFlow<Int>
    val totalReceived: StateFlow<Int>
    val isPaused: StateFlow<Boolean>

    fun togglePause()
    fun markAllRead()
}
```

---

## Key implementation detail — operator chain

```kotlin
private fun notificationStream(): Flow<Notification> = flow {
    var id = 0
    while (true) {
        emit(fakeNotification(id++))
        delay(300)
    }
}

// In ViewModel:
val notifications: StateFlow<List<Notification>> = _isPaused
    .flatMapLatest { paused -> if (paused) emptyFlow() else notificationStream() }
    .distinctUntilChanged { old, new -> old.id == new.id }   // deduplicate by id
    .scan(emptyList<Notification>()) { acc, notif ->          // accumulate
        listOf(notif) + acc
    }
    .conflate()                                               // drop intermediate lists if UI is slow
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
```

---

## Operator cheat sheet for this feature

| Operator | Purpose |
|---|---|
| `distinctUntilChanged` | Skip emission if equal to previous |
| `scan` | Accumulate emissions into a running value (like `fold` but emits each step) |
| `conflate` | Drop intermediate values if collector is slow |
| `buffer` | Decouple producer and collector speeds with a queue |
| `transform` | General-purpose operator — emit 0 or many values per upstream item |
| `take(n)` | Complete the flow after n emissions |

---

## Hints

- `scan` is the Flow equivalent of `runningFold` — it emits the accumulated state after every item
- `conflate` vs `buffer`: `conflate` always delivers the **latest** value; `buffer` queues items and processes all of them
- `distinctUntilChanged()` with a lambda `{ old, new -> old.id == new.id }` lets you define custom equality
- `unreadCount` can be derived: `notifications.map { it.count { n -> !n.isRead } }.stateIn(...)`

---

## Stretch goals

- Group notifications by time bucket ("Just now", "1 min ago") using a `transform` operator
- Add `take(50)` to cap the feed at 50 items and auto-pause
- Persist the notification list across process death with `DataStore`
